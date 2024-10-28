package xyz.icefery.demo.jsp.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbUtil {

    private static final Properties configuration = new Properties();

    static {
        try (InputStream is = DbUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            configuration.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取数据库连接
    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName(configuration.getProperty("jdbc.driver"));
            c = DriverManager.getConnection(
                configuration.getProperty("jdbc.url"),
                configuration.getProperty("jdbc.username"),
                configuration.getProperty("jdbc.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            log.error("failed to load driver or filed to get db connection");
        }
        return c;
    }
}
