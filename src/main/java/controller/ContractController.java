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

    private ContractDAO contractDAO = new ContractDAO();

    public List<Contract> loadAllContract() throws Exception {
        return contractDAO.getAllContract();
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

            switch (result) {
                case -1:
                    return "Lỗi hệ thống! Thêm hợp đồng thất bại.";
                case -2:
                    return "Thêm thất bại: Mã xe " + carID + " không tồn tại trên hệ thống!";
                case -3:
                    return "Thêm thất bại: Mã khách hàng " + customerID + " không tồn tại!";
                default:
                    if (result > 0) {
                        return "Thêm mới hợp đồng thành công!";
                    } else {
                        return "Lỗi thêm hợp đồng.";
                    }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "Lỗi: " + e.getMessage();
        }
    }
}
