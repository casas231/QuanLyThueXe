/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Car {
    private int id;
    private String licensePlate;
    private String carBrand;
    private String carName;
    private int seatQuantity;
    private int price;
    private String status;
    private String image;

    public Car(String licensePlate, String carBrand, String carName, int seatQuantity, int price, String status, String image) {
        this.licensePlate = licensePlate;
        this.carBrand = carBrand;
        this.carName = carName;
        this.seatQuantity = seatQuantity;
        this.price = price;
        this.status = status;
        this.image = image;
    }

    public Car(int id, String licensePlate, String carBrand, String carName, int seatQuantity, int price, String status, String image) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.carBrand = carBrand;
        this.carName = carName;
        this.seatQuantity = seatQuantity;
        this.price = price;
        this.status = status;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarName() {
        return carName;
    }

    public String getImage() {
        return image;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getPrice() {
        return price;
    }

    public int getSeatQuantity() {
        return seatQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSeatQuantity(int seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
