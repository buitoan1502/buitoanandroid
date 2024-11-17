package com.example.caphe.Model;

public class User {
    private int id;
    private String username;
    private String displayName;
    private String email;
    private String phoneNumber;
    private String password;

    public User(int id, String username, String displayName, String email, String phoneNumber, String password) {
        this.id = id;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
