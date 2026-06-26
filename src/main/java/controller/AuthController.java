/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import model.User;

/**
 *
 * @author Admin
 */
public class AuthController {

    private UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) throws Exception {
        // Kiểm tra dữ liệu đầu vào (Validation)
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new Exception("Tài khoản và mật khẩu không được để trống!");
        }

        User user = userDAO.checkLogin(username, password);
        if (user == null) {
            throw new Exception("Tài khoản hoặc mật khẩu không chính xác!");
        }

        return user; // Đăng nhập thành công
    }

    public String register(String username, String password) {
        // 1. Kiểm tra các trường có bị trống không
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        // 2. Kiểm tra độ dài mật khẩu
        if (password.length() < 6) {
            return "Mật khẩu phải từ 6 ký tự trở lên!";
        }

        // 4. Kiểm tra ký tự đặc biệt trong username (tùy chọn)
        if (username.contains(" ")) {
            return "Tài khoản không được chứa khoảng trắng!";
        }

        // 5. Khởi tạo đối tượng Model và ÉP CỐ ĐỊNH role là "USER"
        // Người dùng tự đăng ký thì không bao giờ được phép làm ADMIN
        User newUser = new User(username, password, "user");

        // 6. Gọi DAO để lưu vào SQLite
        boolean isSuccess = userDAO.insertUser(newUser);

        if (isSuccess) {
            return "Đăng ký tài khoản thành công!";
        } else {
            return "Tài khoản đã tồn tại trên hệ thống!";
        }
    }
}
