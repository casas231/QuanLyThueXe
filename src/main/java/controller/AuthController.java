/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import java.sql.Connection;
import java.sql.SQLException;
import model.User;
import utils.SQLConnect;

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
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new Exception("Tài khoản và mật khẩu không được để trống!");
        }

        User user = userDAO.checkLogin(username, password);
        if (user == null) {
            throw new Exception("Tài khoản hoặc mật khẩu không chính xác!");
        }

        return user;
    }

    public String register(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }

        if (password.length() < 6) {
            return "Mật khẩu phải từ 6 ký tự trở lên!";
        }

        if (username.contains(" ")) {
            return "Tài khoản không được chứa khoảng trắng!";
        }

        User newUser = new User(username, password, "user");
        try {
            Connection conn = SQLConnect.connect();
            int userID = userDAO.insertUser(conn, newUser);

            if (userID != -1) {
                return "Đăng ký tài khoản thành công!";
            } else {
                return "Tài khoản đã tồn tại trên hệ thống!";
            }
        } catch (SQLException e) {
            System.err.println(e);
            return "Lỗi hệ thông";
        }

    }
}
