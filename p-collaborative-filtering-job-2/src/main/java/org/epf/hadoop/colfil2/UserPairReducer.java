package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class UserPairReducer extends Reducer<UserPair, Text, UserPair, Text> {
    @Override
    protected void reduce(UserPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        boolean isDirect = false;
        int commonCount = 0;

        for (Text value : values) {
            if (value.toString().equals("0")) {
                isDirect = true;
            } else if (value.toString().equals("1")) {
                commonCount++;
            }
        }

        if (!isDirect && commonCount > 0) {
            context.write(key, new Text(String.valueOf(commonCount)));
        }
    }
}
