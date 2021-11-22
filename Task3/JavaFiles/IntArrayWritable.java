package Task3;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.ArrayWritable;

public class IntArrayWritable extends ArrayWritable {
	
	public IntArrayWritable() {
		super(IntWritable.class); 
	}

    public IntArrayWritable(IntWritable[] values) {
        super(IntWritable.class, values);
    }

    @Override
    public IntWritable[] get() {
        return (IntWritable[]) super.get();
    }

    @Override
    public String toString() {
        IntWritable[] values = get();
        String strings = "";
        for (int i = 0; i < values.length; i++) {
        	strings = strings + values[i].toString();
        	if (i != values.length-1)
        		strings = strings + "\t";
       }
        return strings;
    }
}
