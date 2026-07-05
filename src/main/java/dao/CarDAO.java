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
import model.Car;
import utils.SQLConnect;

/**
 *
 * @author Admin
 */
public class CarDAO {

    public int addCar(Connection conn, Car car) throws SQLException {
        String sql = "INSERT INTO CAR(license_plate, car_brand, car_name, seat, price, status, image) VALUES(?,?,?,?,?,?, ?) RETURNING id";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int generatedId = -1;
            pstmt.setString(1, car.getLicensePlate());
            pstmt.setString(2, car.getCarBrand());
            pstmt.setString(3, car.getCarName());
            pstmt.setInt(4, car.getSeatQuantity());
            pstmt.setInt(5, car.getPrice());
            pstmt.setString(6, car.getStatus());
            pstmt.setString(7, car.getImage());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    generatedId = rs.getInt("id");
                }
            }
            return generatedId;
        } catch (SQLException e) {
            System.err.println(e);
            return -1;
        }
    }

    public List<Car> getAllCar() throws SQLException {
        List<Car> list = new ArrayList<>();
        String sql = "SELECT * FROM CAR";

        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Car c = new Car(
                        rs.getInt("id"),
                        rs.getString("license_plate"),
                        rs.getString("car_brand"),
                        rs.getString("car_name"),
                        rs.getInt("seat"),
                        rs.getInt("price"),
                        rs.getString("status"),
                        rs.getString("image")
                );

                list.add(c);
            }
        }
        return list;
    }

    public List<Car> getAllCarUser() throws SQLException {
        List<Car> list = new ArrayList<>();
        String sql = "SELECT * FROM CAR WHERE status = ?";

        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "Sẵn sàng");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Car c = new Car(
                        rs.getInt("id"),
                        rs.getString("license_plate"),
                        rs.getString("car_brand"),
                        rs.getString("car_name"),
                        rs.getInt("seat"),
                        rs.getInt("price"),
                        rs.getString("status"),
                        rs.getString("image")
                );

                list.add(c);
            }
        }
        return list;
    }

    public boolean deleteCar(int id) throws SQLException {
        String sql = "DELETE FROM CAR WHERE id = ?";
        Connection conn = null;
        try {
            conn = SQLConnect.connect();
            PreparedStatement psCar = conn.prepareStatement(sql);
            psCar.setInt(1, id);
            psCar.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public boolean updateCar(int id, String licensePlate, String carBrand, String carName, int seatQuantityStr, int priceStr, String status, String image) throws SQLException {
        String sql = "UPDATE CAR SET license_plate = ?, car_brand = ?, car_name = ?, seat = ?, price = ?, status = ?, image = ? WHERE id = ?";
        try (Connection conn = SQLConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, licensePlate);
            ps.setString(2, carBrand);
            ps.setString(3, carName);
            ps.setInt(4, seatQuantityStr);
            ps.setInt(5, priceStr);
            ps.setString(6, status);
            ps.setString(7, image);
            ps.setInt(8, id);

            return ps.executeUpdate() > 0;
        }
    }

}
