package youtube;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class youtube{
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		 Job job = Job.getInstance(conf,"youtube");
		 job.setJarByClass(youtube.class);
		 job.setMapperClass(youtube_Mapper.class);
		 job.setReducerClass(youtube_Reducer.class);
		 job.setMapOutputKeyClass(Text.class);
		 job.setMapOutputValueClass(IntWritable.class);
		 job.setOutputKeyClass(Text.class);
		 job.setOutputValueClass(LongWritable.class);
		 FileInputFormat.addInputPath(job, new Path(args[1]));
		 FileOutputFormat.setOutputPath(job, new Path(args[2]));
		 System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}