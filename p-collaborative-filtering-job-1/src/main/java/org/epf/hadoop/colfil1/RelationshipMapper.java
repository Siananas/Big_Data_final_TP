package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RelationshipMapper extends Mapper<LongWritable, Relationship, Text, Text> {
    private Text user1 = new Text();
    private Text user2 = new Text();

    @Override
    protected void map(LongWritable key, Relationship value, Context context) throws IOException, InterruptedException {
        user1.set(value.getId1());
        user2.set(value.getId2());
        context.write(user1, user2);

        user1.set(value.getId2());
        user2.set(value.getId1());
        context.write(user1, user2);
    }
}
