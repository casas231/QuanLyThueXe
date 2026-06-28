/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Customer;
import model.User;
import utils.SQLConnect;

/**
 *
 * @author Admin
 */
public class CustomerDAO {

    public boolean insertCustomer(User user, String fullName, String phone, String idNumber, String driverLicense, String address) {
        String insertCustomerSQL = "INSERT INTO customer(account_id, name, phone, id_number, driver_license, address) VALUES(?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = SQLConnect.connect();
            conn.setAutoCommit(false);

            UserDAO userDAO = new UserDAO();
            int generatedUserId = userDAO.insertUser(conn, user);

            if (generatedUserId != -1) {
                PreparedStatement psCustomer = conn.prepareStatement(insertCustomerSQL);
                psCustomer.setInt(1, generatedUserId);
                psCustomer.setString(2, fullName);
                psCustomer.setString(3, phone);
                psCustomer.setString(4, idNumber);
                psCustomer.setString(5, driverLicense);
                psCustomer.setString(6, address);
                psCustomer.executeUpdate();

                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return false;
    }
}
