/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ContractDAO;
import java.sql.SQLException;
import java.util.List;
import model.Contract;

/**
 *
 * @author Admin
 */
public class ContractController {

    private final ContractDAO contractDAO = new ContractDAO();

    public List<Contract> loadAllContract() throws Exception {
        return contractDAO.getAllContract();
    }

    public List<Contract> loadAllContractById(int id) throws Exception {
        return contractDAO.getAllContractById(id);
    }

    public String createContract(String customerIdRaw, String carIdRaw, String startDate, String endDate, String totalPriceStr, String status) {
        if (customerIdRaw.trim().isEmpty() || carIdRaw.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty() || totalPriceStr.trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ tất cả các thông tin hợp đồng!";
        }

        int customerID = Integer.parseInt(customerIdRaw.trim());
        int carID = Integer.parseInt(carIdRaw.trim());
        int totalPrice = Integer.parseInt(totalPriceStr.trim());

        try {
            int result = contractDAO.insertContract(customerID, carID, startDate, endDate, totalPrice, status);

            return switch (result) {
                case -1 ->
                    "Lỗi hệ thống! Thêm hợp đồng thất bại.";
                case -2 ->
                    "Thêm thất bại: Mã xe " + carID + " không tồn tại trên hệ thống!";
                case -3 ->
                    "Thêm thất bại: Mã khách hàng " + customerID + " không tồn tại!";
                default ->
                    result > 0
                    ? "Thêm mới hợp đồng thành công!"
                    : "Lỗi thêm hợp đồng.";
            };
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Lỗi: " + e.getMessage();
        }
    }

    public String updateContract(int id, String customerIdRaw, String carIdRaw, String startDate, String endDate, String totalPriceStr, String status) {
        if (customerIdRaw.trim().isEmpty() || carIdRaw.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty() || totalPriceStr.trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ tất cả các thông tin hợp đồng!";
        }

        int customerID = Integer.parseInt(customerIdRaw.trim());
        int carID = Integer.parseInt(carIdRaw.trim());
        int totalPrice = Integer.parseInt(totalPriceStr.trim());

        try {
            int result = contractDAO.updateContract(id, customerID, carID, startDate, endDate, totalPrice, status);

            return switch (result) {
                case -1 ->
                    "Lỗi hệ thống! Sửa hợp đồng thất bại.";
                case -2 ->
                    "Sửa hợp đồng thất bại: Mã xe " + carID + " không tồn tại trên hệ thống!";
                case -3 ->
                    "Sửa hợp đồng thất bại: Mã khách hàng " + customerID + " không tồn tại!";
                default ->
                    result > 0
                    ? "Sửa hợp đồng thành công!"
                    : "Lỗi sửa hợp đồng.";
            };
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Lỗi: " + e.getMessage();
        }
    }

    public String removeContract(int id) {
        try {
            boolean isDeleted = contractDAO.deleteContract(id);
            return isDeleted ? "Xóa hợp đồng thành công!" : "Xóa hợp đồng thất bại.";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Không thể xóa hợp đồng";
        }
    }

    public List<Contract> fillContract(String option, String dateStr) throws SQLException {
        if (option.equals("Tìm theo ngày thuê")) {
            return contractDAO.findContract("start_date", dateStr);
        } else if (option.equals("Tìm theo ngày trả")) {
            return contractDAO.findContract("end_date", dateStr);
        }

        return null;
    }

    public String removeContractAndUpdateCar(int contractID, int carID, String licensePlate, String carBrand, String carName, String seatQuantityStr, String priceStr, String status, String image) throws Exception {
        int seatQuantity = Integer.parseInt(seatQuantityStr);
        int price = Integer.parseInt(priceStr);
        try {
            boolean isDeleted = contractDAO.deleteContractAndUpdateCar(contractID, carID, licensePlate, carBrand, carName, seatQuantity, price, status, image);
            return isDeleted ? "Xóa hợp đồng thành công!" : "Xóa hợp đồng thất bại.";
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "Không thể xóa hợp đồng";
        }
    }
}
