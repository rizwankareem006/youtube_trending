package youtube;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class youtube_Mapper 
	extends Mapper<LongWritable, Text, Text, IntWritable> {

	  @Override
	  public void map(LongWritable offset, Text record_txt, Context context)
	      throws IOException, InterruptedException {
		  try {
			  	String record = record_txt.toString();
				String tuple[] = record.split(",");
				System.out.println("tuple length ==> "+ tuple.length);
				if (tuple.length == 10) {
					String publishedAt_st = tuple[2].substring(0, 10);
					LocalDate publishedAt = LocalDate.parse(publishedAt_st);
					LocalDate lower_limit = LocalDate.of(2020, 9, 1);
					LocalDate upper_limit = LocalDate.of(2021, 9, 1);
					int likes = Integer.parseInt(tuple[7]);
					String countryName = tuple[0];
					if ((publishedAt.isAfter(lower_limit) || publishedAt.isEqual(lower_limit))
							&& (publishedAt.isBefore(upper_limit) || publishedAt.isEqual(upper_limit)) && likes > 1000000)
					{
						String videoId = tuple[1];
						
						context.write(new Text(videoId + " - "+ countryName + " - "),  new IntWritable(likes));
					}
		  }}
		  catch(DateTimeParseException dtpe) {
			  return;
		  }

}}
	  