/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CarDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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

    public List<Car> loadAllCar() throws Exception {
        return carDAO.getAllCar();
    }

    public List<Car> loadAllCarUser() throws Exception {
        return carDAO.getAllCarUser();
    }

    public String createCar(String licensePlate, String carBrand, String carName, String seatQuantityStr, String priceStr, String status, String image) {
        if (licensePlate.trim().isEmpty() || carBrand.trim().isEmpty() || carName.trim().isEmpty() || seatQuantityStr.equals("0") || priceStr.trim().isEmpty() || status.trim().isEmpty() || image.trim().isEmpty()) {
            return "Thông tin không được để trống!";
        }

        int seatQuantity = Integer.parseInt(seatQuantityStr);
        int price = Integer.parseInt(priceStr);

        Car newCar = new Car(licensePlate, carBrand, carName, seatQuantity, price, status, image);
        
        try (Connection conn = SQLConnect.connect()) {
            int carID = carDAO.addCar(conn, newCar);

            if (carID != -1) {
                return "Thêm xe thành công!";
            } else {
                return "Xe đã tồn tại trên hệ thống!";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "Lỗi hệ thông";
        }
    }

    public String updateCar(int id, String licensePlate, String carBrand, String carName, String seatQuantityStr, String priceStr, String status, String image) {
        if (licensePlate.trim().isEmpty() || carBrand.trim().isEmpty() || carName.trim().isEmpty() || seatQuantityStr.equals("0") || priceStr.trim().isEmpty() || status.trim().isEmpty() || image.trim().isEmpty()) {
            return "Thông tin không được để trống!";
        }

        int seatQuantity = Integer.parseInt(seatQuantityStr);
        int price = Integer.parseInt(priceStr);

        try {
            boolean isUpdated = carDAO.updateCar(id, licensePlate, carBrand, carName, seatQuantity, price, status, image);
            return isUpdated ? "Cập nhật xe thành công!" : "Không tìm thấy xe để cập nhật.";
        } catch (SQLException e) {
            return "Lỗi cập nhật: " + e.getMessage();
        }
    }

    public String removeCar(int id) {
        try {
            boolean isDeleted = carDAO.deleteCar(id);
            return isDeleted ? "Xóa xe thành công!" : "Xóa xe thất bại.";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Không thể xóa xe";
        }
    }

    public List<Car> fillCarByBrand(String brand) throws SQLException {
        return carDAO.findCarByBrand(brand);
    }

    public Car fillCarById(int id) throws SQLException {
        Car c = carDAO.findCarById(id);
        return c;
    }
}
