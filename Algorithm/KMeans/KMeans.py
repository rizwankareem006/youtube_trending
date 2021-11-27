import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.cluster import KMeans
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

pd.plotting.scatter_matrix(df.iloc[:, 3:7], alpha=0.5)

X_train, X_test = train_test_split(df, test_size=0.2, random_state=42)

def calculate_cost(test_record, grouped_mean):
        means = grouped_mean.loc[test_record['clusters']]
        test_record = test_record.iloc[3:7]
        return np.sqrt(np.sum(np.power(means-test_record, 2)))

cost_vals = []
for i in range(1, 25):
    print("Iteration -", i)
    model = KMeans(n_clusters=i, random_state=42)
    model = model.fit(X_train.iloc[:, 3:7])
    train_clusters = X_train.assign(clusters=model.labels_)
    grouped = train_clusters.groupby(by='clusters', as_index=True)
    grouped_mean = grouped.aggregate({'view_count':'mean', 'likes':'mean', 'dislikes':'mean', 'comment_count':'mean'})    
    predicted_clusters = model.predict(X_test.iloc[:, 3:7])
    test_clusters = X_test.assign(clusters=predicted_clusters)
    cost = np.average(test_clusters.apply(calculate_cost, axis=1, args=(grouped_mean,)))
    cost_vals.append(cost)

plt.plot(range(1, 25), cost_vals)
plt.title("Number of Clusters Vs Cost")
plt.xlabel("Number of Clusters")
plt.ylabel("Cost")

"""
Normalize
---------
max_vals = df.iloc[:, 3:7].max()
min_vals = df.iloc[:, 3:7].min()
print(max_vals)
print(min_vals)
norm1 = (X_train.iloc[:, 3:7]-min_vals)/(max_vals-min_vals)
norm1.head()
"""

no_of_clusters = 8
model = KMeans(n_clusters=no_of_clusters, random_state=42)
model = model.fit(X_train.iloc[:, 3:7])
train_clusters = X_train.assign(clusters=model.labels_)

grouped = train_clusters.groupby(by='clusters', as_index=True)
grouped_size = grouped.size()
grouped_mean = grouped.aggregate({'view_count':'mean', 'likes':'mean', 'dislikes':'mean', 'comment_count':'mean'})

predicted_clusters = model.predict(X_test.iloc[:, 3:7])
test_clusters = X_test.assign(clusters=predicted_clusters)
cost = np.average(test_clusters.apply(calculate_cost, axis=1, args=(grouped_mean,)))

print("Cost -", cost)

grouped_mean.plot(kind='bar', title="Attribute Mean Values for Each Cluster")

container = plt.barh(grouped_size.index, grouped_size, color="brown")
plt.bar_label(container)
plt.title("Number of Videos in each Cluster")
plt.xlabel("Number of Videos")
plt.ylabel("Clusters")

y = grouped_size.index
x = grouped_size
plt.barh(y, width=x, color="brown")
for i, v in enumerate(x):
    plt.text(v + 3, i + .25, str(v), color='red', va='top')

clustered_df = pd.concat([train_clusters, test_clusters])

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

for i in range(2):
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




