package xyz.icefery.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyTest {

    @Autowired
    DataSource dataSource;

    @Test
    public void generate() throws SQLException {
        long start = System.currentTimeMillis();

        int totalCount = 100_0000;
        int batchSize = 1000;
        int batchCount = totalCount / batchSize;

        Function<List<Integer>, String> builder = batch -> {
            String delimiter = " ";
            String s1 = "INSERT INTO t_user(id, username, email) VALUES";
            String s2 = batch.stream().map(it -> String.format("(%s,'%s','%s')", it, "u" + it, "u" + it + "@gmail.com")).collect(Collectors.joining(","));
            return String.join(delimiter, s1, s2);
        };

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();

        statement.execute("TRUNCATE TABLE t_user");

        int id = 0;
        List<Integer> batch = new ArrayList<>(batchSize);
        for (int i = 1; i <= batchCount; i++) {
            for (int j = 1; j <= batchSize; j++) {
                batch.add(id);
                id++;
            }
            String sql = builder.apply(batch);
            statement.execute(sql);
            batch.clear();
        }

        long end = System.currentTimeMillis();
        System.out.printf("totalCount=%s batchSize=%s batchCount=%s cost=%s\n", totalCount, batchSize, batchCount, end - start);
    }
}
