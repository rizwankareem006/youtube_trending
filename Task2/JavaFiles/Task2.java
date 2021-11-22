package Task2;

import CommonClasses.LongArrayWritable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Task2 {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Task2");
		job.setJarByClass(Task2.class);
		job.setMapperClass(Task2_Mapper.class);
		job.setReducerClass(Task2_Reducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongArrayWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(LongArrayWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[1]));
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		System.exit(job.waitForCompletion(true)? 0 : 1);
	}
}
