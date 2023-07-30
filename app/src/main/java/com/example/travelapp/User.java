package com.example.travelapp;
public class User {
    private String name;
    private String number;
    private String email;
    private String password;
    private String gender;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String number, String email, String password, String gender) {
        this.name = name;
        this.number = number;
        this.email=email;
        this.password=password;
        this.gender=gender;
    }

    public User(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

