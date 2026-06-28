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
        return null;
    }

    public int insertUser(Connection conn, User user) throws SQLException {
        String sql = "INSERT INTO ACCOUNT(username, password, role) VALUES(?,?,?) RETURNING id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int generatedId = -1;
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, "user");
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    generatedId = rs.getInt("id");
                }
            }
            return generatedId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
