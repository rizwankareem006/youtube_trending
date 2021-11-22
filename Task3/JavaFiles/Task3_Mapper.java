package Task3;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class Task3_Mapper 
	extends Mapper<LongWritable, Text, IntWritable, LongArrayWritable>{
	
	@Override
	public void map(LongWritable offset, Text record_txt, Context context) 
			throws IOException, InterruptedException{
		try {
			String record = record_txt.toString();
			String tuple[] = record.split(",");
			if (tuple.length == 8) {
				
				if (tuple[1].length() != 20) {
					return;
				}
				String trending_date_st = tuple[1].substring(0, 10);
				LocalDate trending_date = LocalDate.parse(trending_date_st);
				LocalDate lower_limit = LocalDate.of(2020, 9, 1);
				LocalDate upper_limit = LocalDate.of(2021, 9, 1);
				if ((trending_date.isAfter(lower_limit) || trending_date.isEqual(lower_limit))
						&& (trending_date.isBefore(upper_limit) || trending_date.isEqual(upper_limit)))
				{
					IntWritable category = new IntWritable(Integer.parseInt(tuple[0]));
					LongWritable[] value_arr = new LongWritable[5];
					value_arr[0] = new LongWritable(1);
					value_arr[1] = new LongWritable(Long.parseLong(tuple[2]));
					value_arr[2] = new LongWritable(Long.parseLong(tuple[3]));
					value_arr[3] = new LongWritable(Long.parseLong(tuple[4]));
					value_arr[4] = new LongWritable(Long.parseLong(tuple[5]));
					LongArrayWritable value = new LongArrayWritable(value_arr); 
					context.write(category, value);
				}
			}
		}
		catch(DateTimeParseException dtpe) {
			  return;
		  }
	}
}
