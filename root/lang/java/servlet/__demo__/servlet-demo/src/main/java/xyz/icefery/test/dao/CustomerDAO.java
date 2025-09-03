package xyz.icefery.test.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import xyz.icefery.test.entity.Customer;

public class CustomerDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    static {
        // @formatter:off
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (ClassNotFoundException e) { throw new Error("加载驱动失败"); }
        // @formatter:on
    }

    private static Connection getConnection() {
        // @formatter:off
        try { return DriverManager.getConnection(URL, USERNAME, PASSWORD); } catch (SQLException e) { throw new Error("获取连接失败"); }
        // @formatter:on
    }

    public static void create(Customer customer) {
        try (
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO customer (name, gender, birth_date, phone, occupation, note) VALUES (?, ?, ?, ?, ?, ?)")
        ) {
            // 允许 null
            ps.setObject(1, customer.getName());
            ps.setObject(2, customer.getGender());
            ps.setObject(3, customer.getBirthDate());
            ps.setObject(4, customer.getPhone());
            ps.setObject(5, customer.getOccupation());
            ps.setObject(6, customer.getNote());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Customer> queryList() {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT * FROM customer");
            return rs(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Customer> queryListByName(String name) {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM customer WHERE name=?")) {
            // 允许 null
            ps.setObject(1, name);
            ResultSet rs = ps.executeQuery();
            return rs(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Customer> queryListByGender(Integer gender) {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM customer WHERE gender=?")) {
            // 允许 null
            ps.setObject(1, gender);
            ResultSet rs = ps.executeQuery();
            return rs(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Customer> queryListByBirthDate(LocalDate birthDate) {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("SELECT * FROM customer WHERE birth_date=?")) {
            // 允许 null
            ps.setObject(1, birthDate);
            ResultSet rs = ps.executeQuery();
            return rs(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Customer> rs(ResultSet rs) throws SQLException {
        List<Customer> list = new ArrayList<>();
        while (rs.next()) {
            Customer c = new Customer();
            // 允许 null | BIGINT -> Long
            c.setId((Long) rs.getObject("id"));

            c.setName(rs.getString("name"));

            // 允许 null | TINYINT -> Integer
            c.setGender(((Integer) rs.getObject("gender")));

            // 允许 null | DATE -> String -> LocalDate
            String birthDate = rs.getString("birth_date");
            c.setBirthDate(birthDate == null ? null : LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            c.setPhone(rs.getString("phone"));
            c.setOccupation(rs.getString("occupation"));
            c.setNote(rs.getString("note"));

            list.add(c);
        }
        return list;
    }
}
