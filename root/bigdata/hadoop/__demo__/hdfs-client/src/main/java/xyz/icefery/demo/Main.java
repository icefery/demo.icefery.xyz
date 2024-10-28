package xyz.icefery.demo;

import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Main {

    public static final String CLASSPATH = Main.class.getResource("/").toString();

    public static void main(String[] args) throws IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname", "true");
        FileSystem fs = FileSystem.get(URI.create("hdfs://node101:9000"), conf, "root");
        fs.copyToLocalFile(new Path("/input/word.txt"), new Path(CLASSPATH + "/word.txt"));
        fs.close();
    }
}
