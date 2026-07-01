/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Car;

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
}
