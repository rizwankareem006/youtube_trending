package Task3;

import java.io.IOException;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;

public class Task3_Reducer 
	extends Reducer<IntWritable, LongArrayWritable, IntWritable, LongArrayWritable>{
		
	@Override
	public void reduce(IntWritable category, Iterable<LongArrayWritable> values, Context context)
		throws IOException, InterruptedException{
		
		long[] sum_arr = new long[5];
		for (LongArrayWritable value: values) {
			LongWritable[] value_arr = value.get();
			for (int i = 0; i < 5 ; i++) {
				sum_arr[i] += value_arr[i].get();
			}
		}
		
		LongWritable[] value_arr = new LongWritable[5];
		for (int i = 0; i < 5 ; i++) {
			value_arr[i] = new LongWritable(sum_arr[i]);
		}
		LongArrayWritable output_arr = new LongArrayWritable(value_arr);
		context.write(category, output_arr);
	}
}
