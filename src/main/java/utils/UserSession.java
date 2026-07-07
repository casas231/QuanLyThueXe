/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author tanghaian
 */
public class UserSession {
    private static UserSession instance;
    private int id;
    private String username;
    private boolean hasProfile;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        
        return instance;
    }

    public void createUserSession(int userId, String username) {
        this.id = userId;
        this.username = username;
    }

    public void clearSession() {
        this.id = 0;
    }

    public int getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHasProfile() {
        return hasProfile;
    }

    public void setHasProfile(boolean hasProfile) {
        this.hasProfile = hasProfile;
    }
}
