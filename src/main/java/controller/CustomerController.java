/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.text.Normalizer;
import model.User;

/**
 *
 * @author Admin
 */
public class CustomerController {

    private CustomerDAO customerDAO;

    public CustomerController() {
        this.customerDAO = new CustomerDAO();
    }

    public String createCustomer(String fullName, String phone, String idNumber, String driverLicense, String address) throws Exception {
        if (fullName.trim().isEmpty() || phone.trim().isEmpty() || idNumber.trim().isEmpty() || driverLicense.trim().isEmpty() || address.trim().isEmpty()) {
            return "Thông tin không được để trống!";
        }

        String generatedUsername = Normalizer.normalize(fullName, Normalizer.Form.NFD).replaceAll("\\W", "").toLowerCase() + idNumber;
        User newUser = new User(generatedUsername, "123456", "user");
        boolean isSuccess = customerDAO.insertCustomer(newUser, fullName, phone, idNumber, driverLicense, address);
        if (isSuccess) {
            return "Thêm khách hàng thành công!";
        } else {
            return "Khách hàng đã tồn tại trên hệ thống!";
        }
    }
}
