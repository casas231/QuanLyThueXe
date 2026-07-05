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
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
            System.err.println(e.getMessage());
            return -1;
        }
    }

    public boolean deleteUser(int accountID, Connection conn) throws SQLException {
        String sql = "DELETE FROM ACCOUNT WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(Connection conn, int id, String password) {
        String sql = "UPDATE ACCOUNT SET password = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
