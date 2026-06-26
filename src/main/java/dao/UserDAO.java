/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import utils.SQLConnect;

/**
 *
 * @author ducanh123
 */
public class UserDAO {

    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM ACCOUNT WHERE username = ? AND password = ?";

        try (Connection conn = SQLConnect.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu tìm thấy, trả về đối tượng User đầy đủ thông tin
                    return new User(
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu sai tài khoản/mật khẩu
    }

    // Đăng ký tài khoản mới (Nếu cần mở rộng tính năng)
    public boolean insertUser(User user) {
        String sql = "INSERT INTO ACCOUNT(username, password, role) VALUES(?,?,?)";
        try (Connection conn = SQLConnect.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, "user");

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String checkAccount(String user, String pass) {
        String sql = "SELECT * FROM ACCOUNT WHERE username = ? AND password = ?";
        try (Connection conn = SQLConnect.connect()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user);
            statement.setString(2, pass);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if (role.equals("admin")) {
                        return "admin";
                    } else if (role.equals("user")) {
                        return "user";
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return "";
    }
}
