package Task3;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.Writable;

public class LongArrayWritable extends ArrayWritable {
	
	public LongArrayWritable() {
		super(LongWritable.class);
	}
	
    public LongArrayWritable(LongWritable[] values) {
        super(LongWritable.class, values);
    }

    @Override
    public LongWritable[] get() {
    	Writable[] temp = super.get();
        if (temp != null) {
            int n = temp.length;
            LongWritable[] items = new LongWritable[n];
            for (int i = 0; i < temp.length; i++) {
                items[i] = (LongWritable)temp[i];
            }
            return items;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        LongWritable[] values = get();
        String strings = "";
        for (int i = 0; i < values.length; i++) {
        	strings = strings + values[i].toString();
        	if (i != values.length-1)
        		strings = strings + "\t";
       }
        return strings;
    }
}
