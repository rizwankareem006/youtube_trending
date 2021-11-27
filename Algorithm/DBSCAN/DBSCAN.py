
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.cluster import DBSCAN
from sklearn.preprocessing import MinMaxScaler

df = pd.read_csv(r"https://raw.githubusercontent.com/rizwankareem006/youtube_trending/main/Youtube%20Trending%20Dataset/US_youtube_trending_data.csv")
df.drop(labels=['video_id', 'title', 'channelId', 'channelTitle', 'comments_disabled', 'ratings_disabled'], axis=1, inplace=True)
df['trending_date'] = pd.to_datetime(df['trending_date'])
df['publishedAt'] = pd.to_datetime(df['publishedAt'])
lower_limit = pd.to_datetime('2020-09-01T00:00:00Z')
upper_limit = pd.to_datetime('2021-09-01T00:00:00Z')
df = df[(df['trending_date'] <= upper_limit) & (df['trending_date'] >= lower_limit)]

zero_valued_indices = df[(df["view_count"] == 0) | (df["likes"] == 0) | (df["dislikes"] == 0) | (df["comment_count"] == 0)].index
df.drop(index = zero_valued_indices, inplace=True)

scaler = MinMaxScaler()
scaler.fit(df.iloc[:, 3:7])
df.iloc[:, 3:7] = scaler.transform(df.iloc[:, 3:7])

from sklearn.neighbors import NearestNeighbors

neigh = NearestNeighbors(n_neighbors=2)
nbrs = neigh.fit(df.iloc[:, 3:7])
distances, indices = nbrs.kneighbors(df.iloc[:, 3:7])
distances = np.sort(distances, axis=0)
distances = distances[:,1]
plt.plot(distances)

fig, ax = plt.subplots()
ax.plot(distances)
ax.set_title("Distance to the Nearest Point")
ax.set_ylabel("Distance")
ax.set_xlabel("Points")
fig.show()

eps = 0.001
min_samples = 15
model = DBSCAN(eps=eps, min_samples=min_samples)
model = model.fit(df.iloc[:, 3:7])

train_clusters = df.assign(clusters=model.labels_)

grouped = train_clusters.groupby(by='clusters', as_index=True)
grouped_size = grouped.size()
grouped_mean = grouped.aggregate({'view_count':'mean', 'likes':'mean', 'dislikes':'mean', 'comment_count':'mean'})

grouped_mean.plot(kind='bar', title="Attribute Mean Values for Each Cluster")

container = plt.barh(grouped_size.index, grouped_size, color="brown")
plt.bar_label(container)
plt.title("Number of Videos in each Cluster")
plt.xlabel("Number of Videos")
plt.ylabel("Clusters")

us_categories = pd.read_json(r"https://raw.githubusercontent.com/rizwankareem006/youtube_trending/main/Youtube%20Trending%20Dataset/US_category_id.json")
us_categories = pd.json_normalize(us_categories["items"])
us_categories.rename(columns={'snippet.title':'name'}, inplace=True)
us_categories.drop(labels=['kind', 'etag', 'snippet.assignable', 'snippet.channelId'], axis=1, inplace=True)
us_categories['id'] = us_categories['id'].astype(int)

cmap = plt.cm.tab10
colors = cmap(np.linspace(0., 1., us_categories.shape[0]))
colordict={}
labels = us_categories['name']
for l,c in zip(labels,colors):
    #print(l,c)
    colordict[l]=c

clustered_df = train_clusters

for i in range(3):
    fig, axs = plt.subplots(nrows=2, ncols=2)
    cats = []
    for j in range(2):
        for k in range(2):
            cluster_no = 4*i + 2*j + k
            cluster = clustered_df[(clustered_df['clusters'] == cluster_no)]
            grouped_cluster = cluster.groupby(by='categoryId', as_index=True)
            labels = us_categories[us_categories['id'].isin(grouped_cluster.size().index)]['name']
            pie_wedge_collection = axs[j, k].pie(grouped_cluster.size(), labels=labels, shadow=True, autopct="%1.1f%%", pctdistance=0.6)
            axs[j, k].set_title("Categories in Cluster {}".format(cluster_no))
            for pie_wedge in pie_wedge_collection[0]:
                pie_wedge.set_edgecolor('white')
                pie_wedge.set_facecolor(colordict[pie_wedge.get_label()])
            cats.append(cluster_no)
    fig.suptitle("Categories in Cluster {}, {}, {}, {}".format(cats[0], cats[1], cats[2], cats[3]), fontsize=18)
    fig.show()

fig, axs = plt.subplots(nrows=1, ncols=2)
cats = []
for k in range(12, 14):
    cluster_no = k
    cluster = clustered_df[(clustered_df['clusters'] == cluster_no)]
    grouped_cluster = cluster.groupby(by='categoryId', as_index=True)
    labels = us_categories[us_categories['id'].isin(grouped_cluster.size().index)]['name']
    pie_wedge_collection = axs[k-12].pie(grouped_cluster.size(), labels=labels, shadow=True, autopct="%1.1f%%", pctdistance=0.6)
    axs[k-12].set_title("Categories in Cluster {}".format(cluster_no))
    for pie_wedge in pie_wedge_collection[0]:
        pie_wedge.set_edgecolor('white')
        pie_wedge.set_facecolor(colordict[pie_wedge.get_label()])
    cats.append(cluster_no)
fig.suptitle("Categories in Cluster {}, {}".format(cats[0], cats[1]), fontsize=18)
fig.show()


