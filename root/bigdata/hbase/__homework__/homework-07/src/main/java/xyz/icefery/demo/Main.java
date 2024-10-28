package xyz.icefery.demo;

import java.io.IOException;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        HBaseTemplate template = new HBaseTemplate() {};

        // exists
        boolean exists = template.exists(connection, "students");
        System.out.println("exists=" + exists + "\n");

        if (exists) {
            // put
            template.put(connection, "student", "index", "base", "indexes", "001,002,003");
            // get
            String baseIndexes = template.get(connection, "student", "index", "base", "indexes");
            System.out.println("baseIndexes=" + baseIndexes + "\n");
            // scan
            List<String> list = template.scan(connection, "student");
            for (String row : list) {
                String[] cells = row.split("\t");
                if (cells.length != 1) {
                    String[] arr = cells[0].split("\\|");
                    String rowKey = arr[0];
                    String name = arr[2];
                    String english = cells[5].split("\\|")[2];
                    String java = cells[6].split("\\|")[2];
                    System.out.println(String.format("%s(%s):%s %s", name, rowKey, english, java) + "\n");
                }
            }
        }
    }
}
