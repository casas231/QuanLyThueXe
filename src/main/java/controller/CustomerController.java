/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.List;
import model.Customer;
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

    public List<Customer> loadAllCustomers() throws Exception {
        return customerDAO.getAllCustomers();
    }

    public int loadCustomerQuantity() {
        return customerDAO.getCustomerQuantity();
    }

    public String createCustomer(String fullName, String phone, String idNumber, String driverLicense, String address) throws Exception {
        if (fullName.trim().isEmpty() || phone.trim().isEmpty() || idNumber.trim().isEmpty() || driverLicense.trim().isEmpty() || address.trim().isEmpty()) {
            return "Thông tin không được để trống!";
        }

        String generatedUsername = Normalizer.normalize(fullName, Normalizer.Form.NFD).toLowerCase().replace("đ", "d").replaceAll("\\W", "") + idNumber;
        User newUser = new User(generatedUsername, "123456", "user");
        boolean isSuccess = customerDAO.insertCustomer(newUser, fullName, phone, idNumber, driverLicense, address);
        if (isSuccess) {
            return "Thêm khách hàng thành công!";
        } else {
            return "Khách hàng đã tồn tại trên hệ thống!";
        }
    }

    public String updateCustomer(int id, String fullName, String phone, String idNumber, String driverLicense, String address) {
        if (fullName.trim().isEmpty() || phone.trim().isEmpty() || idNumber.trim().isEmpty() || driverLicense.trim().isEmpty() || address.trim().isEmpty()) {
            return "Thông tin không được để trống!";
        }
        try {
            boolean isUpdated = customerDAO.updateCustomer(id, fullName, phone, idNumber, driverLicense, address);
            return isUpdated ? "Cập nhật thông tin thành công!" : "Không tìm thấy khách hàng để cập nhật.";
        } catch (SQLException e) {
            return "Lỗi cập nhật: " + e.getMessage();
        }
    }

    public String removeCustomer(int id, int accountID) {
        try {
            boolean isDeleted = customerDAO.deleteCustomer(id, accountID);
            return isDeleted ? "Xóa khách hàng thành công!" : "Xóa khách hàng thất bại.";
        } catch (Exception e) {
            return "Không thể xóa khách hàng";
        }
    }

    public Customer fillCustomer(String idNumber) throws SQLException {
        Customer c = customerDAO.findCustomer(idNumber);
        return c;
    }
}
