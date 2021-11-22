package Task1;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;


public class Task1_5_Mapper 
	extends Mapper<LongWritable, Text, Text, IntWritable> {

	  @Override
	  public void map(LongWritable offset, Text record_txt, Context context)
	      throws IOException, InterruptedException {
		  try {
			  	String record = record_txt.toString();
				String tuple[] = record.split(",");
				if (tuple.length == 9) {
					
					if (tuple[2].length() != 20) {
						return;
					}
					String trending_date_st = tuple[2].substring(0, 10);
					LocalDate trending_date = LocalDate.parse(trending_date_st);
					LocalDate lower_limit = LocalDate.of(2020, 9, 1);
					LocalDate upper_limit = LocalDate.of(2021, 9, 1);
					if ((trending_date.isAfter(lower_limit) || trending_date.isEqual(lower_limit))
							&& (trending_date.isBefore(upper_limit) || trending_date.isEqual(upper_limit)))
					{
						String channel_id = tuple[0];
						int comments_count = Integer.parseInt(tuple[6]);
						context.write(new Text(channel_id), new IntWritable(comments_count));
					}
				}
		  }
		  catch(DateTimeParseException dtpe) {
			  return;
		  }
	  }
}
