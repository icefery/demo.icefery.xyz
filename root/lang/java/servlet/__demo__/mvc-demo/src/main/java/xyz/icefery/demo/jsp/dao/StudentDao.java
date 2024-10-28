package xyz.icefery.demo.jsp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import xyz.icefery.demo.jsp.bean.Student;
import xyz.icefery.demo.jsp.util.DbUtil;

public class StudentDao {

    public static void insertStudent(Student student) {
        try (Connection c = DbUtil.getConnection()) {
            if (c == null) {
                return;
            }
            PreparedStatement ps = c.prepareStatement("INSERT INTO _stu_info (_stu_id, _name, _pwd) VALUES (?, ?, ?)");
            ps.setString(1, student.getStuId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getPwd());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Student selectStudent(String stuId) {
        Student student = null;
        try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT * FROM _stu_info WHERE _stu_id=" + stuId);
            if (rs.next()) {
                student = new Student()
                    .setStuId(stuId)
                    .setName(rs.getString("_name"))
                    .setPwd(rs.getString("_pwd"))
                    .setSex(rs.getInt("_sex"))
                    .setDob(rs.getTimestamp("_dob"))
                    .setNativePlace(rs.getString("_native_place"))
                    .setAddr(rs.getString("_addr"))
                    .setEmail(rs.getString("_email"))
                    .setEnabled(rs.getInt("_enabled"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public static List<Student> selectStudentAll() {
        List<Student> list = new ArrayList<>();
        try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT  * FROM _stu_info");
            while (rs.next()) {
                list.add(
                    new Student()
                        .setStuId(rs.getString("_stu_id"))
                        .setName(rs.getString("_name"))
                        .setPwd(rs.getString("_pwd"))
                        .setSex(rs.getInt("_sex"))
                        .setDob(rs.getTimestamp("_dob"))
                        .setNativePlace(rs.getString("_native_place"))
                        .setAddr(rs.getString("_addr"))
                        .setEmail(rs.getString("_email"))
                        .setEnabled(rs.getInt("_enabled"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateStudent(Student student) {
        try (Connection c = DbUtil.getConnection()) {
            if (c == null) {
                return;
            }
            PreparedStatement ps = c.prepareStatement(
                "UPDATE _stu_info SET _name=?, _pwd=?, _sex=?, _dob=?, _native_place=?, _addr=?, _email=?, _enabled=? WHERE _stu_id=?"
            );
            ps.setString(1, student.getName());
            ps.setString(2, student.getPwd());
            ps.setInt(3, student.getSex());
            ps.setTimestamp(4, student.getDob());
            ps.setString(5, student.getNativePlace());
            ps.setString(6, student.getAddr());
            ps.setString(7, student.getEmail());
            ps.setInt(8, student.getEnabled());
            ps.setString(9, student.getStuId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetStudentPassword(String stuId) {
        try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement()) {
            s.executeUpdate("UPDATE _stu_info SET _pwd=" + stuId + " WHERE _stu_id=" + stuId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudentPermission(String stuId, Integer enabled) {
        try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement()) {
            s.executeUpdate("UPDATE _stu_info SET _enabled=" + enabled + " WHERE _stu_id=" + stuId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(String stuId) {
        try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement()) {
            s.execute("DELETE FROM _stu_info WHERE _stu_id=" + stuId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
