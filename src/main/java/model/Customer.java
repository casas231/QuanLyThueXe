/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Customer {

    private int accountID;
    private String fullName;
    private String phone;
    private String idNumber;
    private String driverLicense;
    private String address;

    public Customer(int accountID, String fullName, String phone, String idNumber, String driverLicense, String address) {
        this.accountID = accountID;
        this.fullName = fullName;
        this.phone = phone;
        this.idNumber = idNumber;
        this.driverLicense = driverLicense;
        this.address = address;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public String getAddress() {
        return address;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
