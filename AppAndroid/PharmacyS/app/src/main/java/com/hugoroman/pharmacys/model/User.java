package com.hugoroman.pharmacys.model;

public class User {

    private String email;
    private String name;
    private String surname;

    public User(String email, String name, String surname) {

        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
