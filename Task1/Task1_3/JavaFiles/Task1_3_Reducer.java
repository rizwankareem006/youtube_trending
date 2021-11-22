package Task1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Task1_3_Reducer 
	extends Reducer<Text, IntWritable, Text, LongWritable>{
	
	@Override
	public void reduce(Text channelId, Iterable<IntWritable> counts, Context context)
	         throws IOException,  InterruptedException {

	      long sum  = 0;
	      for (IntWritable count  : counts) {
	        sum += count.get();
	      }
	      context.write(channelId,  new LongWritable(sum));
	}
}
