package org.epf.hadoop.colfil1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ColFilJob1 {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: RelationshipJob <input path> <output path>");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "RelationshipJob");
        job.setJarByClass(ColFilJob1.class);

        // Mapper et Reducer
        job.setMapperClass(RelationshipMapper.class);
        job.setReducerClass(RelationshipReducer.class);

        // InputFormat personnalisé
        job.setInputFormatClass(RelationshipInputFormat.class);

        // Clés et valeurs
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Nombre de reducers
        job.setNumReduceTasks(2);

        // Chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
