/*
 * The task is to find the channel which got most likes in the trending list.
 * 
 * The output displays all the channels with their channelId and the sum of likes they got for all 
 * their videos in the trending list 
 */
package Task1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task1_3 {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		 Job job = Job.getInstance(conf, "Task1_3");
		 job.setJarByClass(Task1_3.class);
		 job.setMapperClass(Task1_3_Mapper.class);
		 job.setReducerClass(Task1_3_Reducer.class);
		 job.setMapOutputKeyClass(Text.class);
		 job.setMapOutputValueClass(IntWritable.class);
		 job.setOutputKeyClass(Text.class);
		 job.setOutputValueClass(LongWritable.class);
		 FileInputFormat.addInputPath(job, new Path(args[1]));
		 FileOutputFormat.setOutputPath(job, new Path(args[2]));
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
