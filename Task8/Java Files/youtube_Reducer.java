package youtube;


import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class youtube_Reducer 
	extends Reducer<Text, IntWritable, Text, LongWritable>{


			@Override
			public void reduce(Text video_id, Iterable<IntWritable> counts, Context context)
			         throws IOException,  InterruptedException {
			        
			        long maxLikes = 0;
			       // List<String> coutryNames = new ArrayList<>();
			        for (IntWritable count : counts) {
			            if(count.get() > maxLikes){
			            	maxLikes = count.get();
			            }
			        }

			      context.write(new Text(video_id ),  
			    		  new LongWritable(maxLikes));
			}
}
