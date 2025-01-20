package org.epf.hadoop.colfil1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

public class RelationshipReducer extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> relations = new HashSet<>();
        for (Text value : values) {
            relations.add(value.toString());
        }
        String formattedRelations = String.join(",", relations);
        context.write(key, new Text(formattedRelations));
    }
}
