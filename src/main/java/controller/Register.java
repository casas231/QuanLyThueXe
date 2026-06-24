/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AccountDAO;

/**
 *
 * @author Admin
 */
public class Register {
    private AccountDAO accountDao = new AccountDAO();
    public String register(String username, String password) {
       if (username.isEmpty() || password.isEmpty()) {
            return null;
        }

        return accountDao.checkAccount(username, password);
    }
}
