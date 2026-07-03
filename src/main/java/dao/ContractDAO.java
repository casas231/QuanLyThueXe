/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Contract;
import utils.SQLConnect;

/**
 *
 * @author Admin
 */
public class ContractDAO {

    public List<Contract> getAllContract() throws SQLException {
        List<Contract> list = new ArrayList<>();
        String sql = "SELECT * FROM CONTRACT";

        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Contract c = new Contract(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("car_id"),
                        rs.getString("start_date"),
                        rs.getString("end_date"),
                        rs.getInt("total_price"),
                        rs.getString("status")
                );

                list.add(c);
            }
        }
        return list;
    }

    public int insertContract(int customerID, int carID, String startDate, String endDate, int totalPrice, String status) throws SQLException {
        String existCustomerSQL = "SELECT * FROM CUSTOMER WHERE id = ?";
        String existCarSQL = "SELECT * FROM CAR WHERE id = ?";
        String sql = "INSERT INTO CONTRACT(customer_id, car_id, start_date, end_date, total_price, status) VALUES(?,?,?,?,?,?) RETURNING id";

        Connection conn = null;
        try {
            conn = SQLConnect.connect();
            PreparedStatement psExistCustomer = conn.prepareStatement(existCustomerSQL);
            psExistCustomer.setInt(1, customerID);
            ResultSet rsCustomer = psExistCustomer.executeQuery();
            if (rsCustomer.next()) {
                try {
                    PreparedStatement psExistCar = conn.prepareStatement(existCarSQL);
                    psExistCar.setInt(1, carID);
                    ResultSet rsCar = psExistCar.executeQuery();
                    if (rsCar.next()) {
                        try {
                            PreparedStatement pstmt = conn.prepareStatement(sql);
                            int generatedId = -1;
                            pstmt.setInt(1, customerID);
                            pstmt.setInt(2, carID);
                            pstmt.setString(3, startDate);
                            pstmt.setString(4, endDate);
                            pstmt.setInt(5, totalPrice);
                            pstmt.setString(6, status);

                            ResultSet rs = pstmt.executeQuery();
                            if (rs.next()) {
                                generatedId = rs.getInt("id");
                                return generatedId;
                            }

                            return generatedId;
                        } catch (SQLException e) {
                            System.err.println(e);
                            return -1;
                        }
                    }
                    return -2;
                } catch (SQLException e) {
                    System.err.println(e);
                    return -1;
                }
            }
            return -3;

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            conn.close();
        }
        return -1;
    }

}
