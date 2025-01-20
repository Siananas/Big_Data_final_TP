package org.epf.hadoop.colfil3;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecommendationReducer extends Reducer<Text, Text, Text, Text> {
    private Text result = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        ArrayList<String> recommendations = new ArrayList<>();

        // Collecter toutes les recommandations
        for (Text value : values) {
            recommendations.add(value.toString());
        }

        // Trier par nombre de relations communes (décroissant), puis par ordre alphabétique
        recommendations.sort((r1, r2) -> {
            String[] parts1 = r1.split(":");
            String[] parts2 = r2.split(":");

            int count1 = Integer.parseInt(parts1[1]);
            int count2 = Integer.parseInt(parts2[1]);

            // Trier par nombre de relations en commun (décroissant)
            if (count1 != count2) {
                return Integer.compare(count2, count1);
            }

            // Trier par ordre alphabétique des noms
            return parts1[0].compareTo(parts2[0]);
        });

        // Conserver uniquement les 5 meilleures recommandations
        StringBuilder top5 = new StringBuilder();
        for (int i = 0; i < Math.min(5, recommendations.size()); i++) {
            if (i > 0) {
                top5.append(",");
            }
            top5.append(recommendations.get(i));
        }

        result.set(top5.toString());
        context.write(key, result);
    }
}
