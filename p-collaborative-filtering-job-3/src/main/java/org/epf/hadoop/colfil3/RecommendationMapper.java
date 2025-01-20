package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class RecommendationMapper extends Mapper<Object, Text, Text, Text> {
    private Text user = new Text();
    private Text recommendation = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] parts = value.toString().split("\\s+");
        if (parts.length != 2) return;

        String[] users = parts[0].split(",");
        if (users.length != 2) return;

        String user1 = users[0];
        String user2 = users[1];
        String commonCount = parts[1];

        // Émettre deux recommandations
        user.set(user1);
        recommendation.set(user2 + ":" + commonCount);
        context.write(user, recommendation);

        user.set(user2);
        recommendation.set(user1 + ":" + commonCount);
        context.write(user, recommendation);
    }
}
