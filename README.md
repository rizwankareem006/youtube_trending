
# Youtube Trending Analysis

## Task 1
Find the channels which got the highest number of videos in the trending list, likes, dislikes, comments and views.
### Needed Columns
1. Channel Id(channelId)
2. Channel Title(channelTitle)
3. Trending Date(trending_date)
4. View Count(view_count)
5. Likes Count(likes)
6. Dislikes Count(dislikes)
7. Comments Count(comment_count)
#### Note-
Inside the ‘Task1’ folder, there would be folders for each subtasks. Inside each subtask folder there would be a folder named output, in that folder there would be a file called ‘part-r-00000’ which holds the actual output.
### Task 1_1
Find the channel which got the highest number of videos in the trending list
#### Output
channelId   no_of_appearances
### Task 1_2
Find the channel which got the highest number of views in the trending list
#### Output
channelId   no_of_views
### Task 1_3
Find the channel which got the highest number of likes in the trending list
#### Output
channelId   no_of_likes
### Task 1_4
Find the channel which got the highest number of dislikes in the trending list
#### Output
channelId   no_of_dislikes
### Task 1_5
Find the channel which got the highest number of comments in the trending list
#### Output
channelId   no_of_comments
### Task 1_6
Find the channel name for each channel id
#### Output
channelId   channel_name

## Task 2
Find the video which got the highest number of appearances, likes, dislikes, comments and views in the trending list
### Needed Columns in dataset
1. Video ID(categoryId)
2. Trending Date(trending_date)
3. View Count(view_count)
4. Likes Count(likes)
5. Dislikes Count(dislikes)
6. Comments Count(comment_count)
#### Note-
Inside the Task2 folder, there will be a folder named output which will have a file named ‘part-r-00000’ containing the mapreduce outputs. The file contains six distinct columns.
### Output Columns
1. Video ID
2. No of Appearances
3. Views
4. Likes
5. Dislikes
6. Comments

## Task 3
Find the category which got the highest number of likes, dislikes, comments, views and videos in the trending list
### Needed Columns in dataset
1. Category ID(categoryId)
2. Trending Date(trending_date)
3. View Count(view_count)
4. Likes Count(likes)
5. Dislikes Count(dislikes)
6. Comments Count(comment_count)
#### Note-
Inside the Task3 folder, there will be a folder named output which will have a file named ‘part-r-00000’ containing the mapreduce outputs. The file contains six distinct columns.
### Output Columns
1. Category ID
2. No of Appearances
3. Views
4. Likes
5. Dislikes
6. Comments

## Task 4
Find the month which got the highest number of likes, dislikes, comments, views and videos in the trending list
### Needed Columns in the dataset
1. Published Date(publishedAt)
2. Trending Date(trending_date)
3. View Count(view_count)
4. Likes Count(likes)
5. Dislikes Count(dislikes)
6. Comments Count(comment_count)
#### Note-
Inside the Task4 folder, there will be a folder named output which will have a file named ‘part-r-00000’ containing the mapreduce outputs. The file contains six distinct columns.
### Output Columns
1. Published Month
2. No of Appearances
3. Views
4. Likes
5. Dislikes
6. Comments

## Task 5
Find the day which got the highest number of likes, dislikes, comments, views and videos in the trending list
### Needed Columns in the dataset
1. Published Date(publishedAt)
2. Trending Date(trending_date)
3. View Count(view_count)
4. Likes Count(likes)
5. Dislikes Count(dislikes)
6. Comments Count(comment_count)
#### Note-
Inside the Task5 folder, there will be a folder named output which will have a file named ‘part-r-00000’ containing the mapreduce outputs. The file contains six distinct columns.
### Output Columns
1. Published Day
2. No of Appearances
3. Views
4. Likes
5. Dislikes
6. Comments

## Task 7
Find the time (hour of the day) which got the highest number of likes, dislikes, comments, views and videos in the trending list
### Needed Columns
1. Published Time(publishedAt)
2. Trending Date(trending_date)
3. View Count(view_count)
4. Likes Count(likes)
5. Dislikes Count(dislikes)
6. Comments Count(comment_count)
#### Note-
Inside the Task7 folder, there will be a folder named output which will have a file named ‘part-r-00000’ containing the mapreduce outputs. The file contains six distinct columns.
### Output Columns
1. Published Time
2. No of Appearances
3. Views
4. Likes
5. Dislikes
6. Comments

## Task 9
Find the ratio of number of likes is to views, number of dislikes is to views and number of comments is to views on each category for all the countries and do a comparative study.

### Hive Query
> SELECT COUNTRY, CATEGORY_ID, SUM(LIKES)/SUM(VIEW_COUNT), SUM(DISLIKES)/SUM(VIEW_COUNT), SUM(COMMENT_COUNT)/SUM(VIEW_COUNT)
FROM YOUTUBE_TRENDING
GROUP BY COUNTRY, CATEGORY_ID;

### Output Columns
1) Country
2) Category_Id
3) Ratio of likes is to view_count
4) Ratio of dislikes is to view_count
5) Ratio of comment_count is to view_count

## Task 10 
Find the maximum views, likes, dislikes and comments in each country.

### Hive Query -
> SELECT COUNTRY, MAX(VIEW_COUNT), MAX(LIKES), MAX(DISLIKES), MAX(COMMENT_COUNT)
FROM YOUTUBE_TRENDING
GROUP BY COUNTRY;

### Output Columns
1) Country
2) Highest number of views for a single video in that country
3) Highest number of likes for a single video in that country
4) Highest number of dislikes for a single video in that country
5) Highest number of comments for a single video in that country

## Task 11
Find the average views, likes, dislikes and comments in each country.

### Hive Query
> SELECT COUNTRY, AVG(VIEW_COUNT), AVG(LIKES), AVG(DISLIKES), AVG(COMMENT_COUNT)
FROM YOUTUBE_TRENDING
GROUP BY COUNTRY;

### Output Columns
1) Country
2) Average number of views for a single video in that country
3) Average number of likes for a single video in that country
4) Average number of dislikes for a single video in that country
5) Average number of comments for a single video in that country

## Task 12
Find the number of appearances, and average number of views, likes, dislikes and comments in each category.

### Hive Query
> SELECT CATEGORY_ID, COUNT(*), AVG(VIEW_COUNT), AVG(LIKES), AVG(DISLIKES), AVG(COMMENT_COUNT)
FROM YOUTUBE_TRENDING
GROUP BY CATEGORY_ID;

### Output Columns 
1) Country
2) No of appearances in that category
3) Average number of views for a single video in that category
4) Average number of likes for a single video in that category
5) Average number of dislikes for a single video in that category
6) Average number of comments for a single video in that categroy
