/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CarDAO;
import java.sql.Connection;
import java.sql.SQLException;
import model.Car;
import utils.SQLConnect;

/**
 *
 * @author Admin
 */
public class CarController {

    private final CarDAO carDAO;

    public CarController() {
        this.carDAO = new CarDAO();
    }

    public String createCar(String licensePlate, String carBrand, String carName, String seatQuantityStr, String priceStr, String status, String image) throws Exception {

        if (licensePlate.trim().isEmpty() || carBrand.trim().isEmpty() || carName.trim().isEmpty() || seatQuantityStr.equals("0") || priceStr.trim().isEmpty() || status.trim().isEmpty() || image.trim().isEmpty()) {
            return "Thông tin không được để trống!";
        }

        int seatQuantity = Integer.parseInt(seatQuantityStr);
        int price = Integer.parseInt(priceStr);

        Car newCar = new Car(licensePlate, carBrand, carName, seatQuantity, price, status, image);
        Connection conn = null;
        try {
            conn = SQLConnect.connect();
            int carID = carDAO.addCar(conn, newCar);

            if (carID != -1) {
                return "Thêm xe thành công!";
            } else {
                return "Xe đã tồn tại trên hệ thống!";
            }
        } catch (SQLException e) {
            System.err.println(e);
            return "Lỗi hệ thông";
        } finally {
            conn.close();
        }
    }
}
