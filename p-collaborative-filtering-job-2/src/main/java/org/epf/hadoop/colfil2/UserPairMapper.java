package org.epf.hadoop.colfil2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;

public class UserPairMapper extends Mapper<LongWritable, Text, UserPair, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\\s+");
        if (parts.length != 2) return;

        String user = parts[0];
        String[] friends = parts[1].split(",");

        // Émettre chaque combinaison possible
        HashSet<String> friendSet = new HashSet<>();
        for (String friend : friends) {
            friendSet.add(friend);
            context.write(new UserPair(user, friend), new Text("0")); // Relation directe
        }

        String[] friendsArray = friendSet.toArray(new String[0]);
        for (int i = 0; i < friendsArray.length; i++) {
            for (int j = i + 1; j < friendsArray.length; j++) {
                context.write(new UserPair(friendsArray[i], friendsArray[j]), new Text("1")); // Relation commune
            }
        }
    }
}
