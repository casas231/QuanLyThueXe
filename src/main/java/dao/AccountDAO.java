/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.SQLConnect;

/**
 *
 * @author ducanh123
 */
public class AccountDAO {
    
    public static String checkAccount(String user, String pass) {
        String sql = "SELECT * FROM ACCOUNT WHERE username = ? AND password = ?";
        try (Connection conn = SQLConnect.connect()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user);
            statement.setString(2, pass);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    if (role.equals("admin")) return "admin";
                    else if (role.equals("user")) return "user";
                }
            }
        }
        catch (SQLException e) {
            System.err.println(e);
        }
        return "";
    }
}
