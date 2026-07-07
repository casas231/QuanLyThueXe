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
        String sql = "SELECT * FROM CONTRACT";
        List<Contract> list = new ArrayList<>();

        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
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

    public List<Contract> getAllContractById(int id) throws SQLException {
        String sql = "SELECT * FROM CONTRACT WHERE customer_id = ?";
        List<Contract> list = new ArrayList<>();
        
        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

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

    public int insertContract(int customerID, int carID, String startDate, String endDate, int totalPrice, String status) {
        String existCustomerSQL = "SELECT * FROM CUSTOMER WHERE id = ?";
        String existCarSQL = "SELECT * FROM CAR WHERE id = ?";
        String sql = "INSERT INTO CONTRACT(customer_id, car_id, start_date, end_date, total_price, status) VALUES(?,?,?,?,?,?) RETURNING id";

        try (Connection conn = SQLConnect.connect(); PreparedStatement psExistCustomer = conn.prepareStatement(existCustomerSQL);) {
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
                            System.err.println(e.getMessage());
                            return -1;
                        }
                    }
                    
                    return -2;
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    return -1;
                }
            }
            
            return -3;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return -1;
    }

    public int updateContract(int id, int customerID, int carID, String startDate, String endDate, int totalPrice, String status) {
        String existCustomerSQL = "SELECT * FROM CUSTOMER WHERE id = ?";
        String existCarSQL = "SELECT * FROM CAR WHERE id = ?";
        String sql = "UPDATE CONTRACT SET customer_id = ?, car_id = ?, start_date = ?, end_date = ?, total_price = ?, status = ? WHERE id = ?";

        try (Connection conn = SQLConnect.connect()) {
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
                            PreparedStatement ps = conn.prepareStatement(sql);
                            
                            ps.setInt(1, customerID);
                            ps.setInt(2, carID);
                            ps.setString(3, startDate);
                            ps.setString(4, endDate);
                            ps.setInt(5, totalPrice);
                            ps.setString(6, status);
                            ps.setInt(7, id);

                            int rs = ps.executeUpdate();
                            
                            if (rs > 0) {
                                return 1;
                            }
                        } catch (SQLException e) {
                            System.err.println(e.getMessage());
                            return -1;
                        }
                    }
                    
                    return -2;
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    return -1;
                }
            }
            
            return -3;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return -1;
    }

    public boolean deleteContract(int id) {
        String sql = "DELETE FROM CONTRACT WHERE id = ?";
        
        try (Connection conn = SQLConnect.connect()) {
            PreparedStatement psContract = conn.prepareStatement(sql);
            psContract.setInt(1, id);
            psContract.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    public boolean deleteContractAndUpdateCar(int contractID, int carID, String licensePlate, String carBrand, String carName, int seatQuantityStr, int priceStr, String status, String image) {
        String updateCarSQL = "UPDATE CAR SET license_plate = ?, car_brand = ?, car_name = ?, seat = ?, price = ?, status = ?, image = ? WHERE id = ?";
        Connection conn = null;
        
        try {
            conn = SQLConnect.connect();
            conn.setAutoCommit(false);

            boolean isSuccess = deleteContract(contractID);

            if (isSuccess == false) {
                conn.rollback();
                return false;
            }
            
            try (PreparedStatement psContract = conn.prepareStatement(updateCarSQL)) {
                psContract.setString(1, licensePlate);
                psContract.setString(2, carBrand);
                psContract.setString(3, carName);
                psContract.setInt(4, seatQuantityStr);
                psContract.setInt(5, priceStr);
                psContract.setString(6, status);
                psContract.setString(7, image);
                psContract.setInt(8, carID);

                psContract.executeUpdate();
            }

            conn.commit();
            
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            
            System.err.println(e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    public List<Contract> findContract(String option, String value) throws SQLException {
        List<Contract> list = new ArrayList<>();
        String sql = "SELECT * FROM CONTRACT WHERE " + option + " = ?";

        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            
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
}
