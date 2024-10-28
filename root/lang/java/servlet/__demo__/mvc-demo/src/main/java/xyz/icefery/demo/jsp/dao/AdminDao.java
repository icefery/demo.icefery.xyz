package xyz.icefery.demo.jsp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import xyz.icefery.demo.jsp.bean.Admin;
import xyz.icefery.demo.jsp.util.DbUtil;

public class AdminDao {

    public static Admin selectAdmin(String adminId) {
        Admin admin = null;
        try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT _pwd FROM _admin WHERE _admin_id=" + adminId);
            if (rs.next()) {
                admin = new Admin().setAdminId(adminId).setPassword(rs.getString("_pwd"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
