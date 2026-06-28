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
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.User;
import utils.SQLConnect;

/**
 *
 * @author Admin
 */
public class CustomerDAO {

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER";

        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getInt("account_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("id_number"),
                        rs.getString("driver_license"),
                        rs.getString("address")
                );
                list.add(c);
            }
        }
        return list;
    }

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

    public boolean updateCustomer(int id, String fullName, String phone, String idNumber, String driverLicense, String address) throws SQLException {
        String sql = "UPDATE CUSTOMER SET name = ?, phone = ?, id_number = ?, driver_license = ?, address = ? WHERE id = ?";
        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, phone);
            ps.setString(3, idNumber);
            ps.setString(4, driverLicense);
            ps.setString(5, address);
            ps.setInt(6, id);

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM CUSTOMER WHERE id = ?";
        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public Customer findCustomer(String idNumber) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER WHERE id_number = ?";
        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getInt("account_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("id_number"),
                        rs.getString("driver_license"),
                        rs.getString("address")
                );

                return c;
            }
            return null;
        }
    }
}
