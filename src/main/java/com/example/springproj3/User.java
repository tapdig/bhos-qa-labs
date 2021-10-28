package com.example.springproj3;

public class User {
    public String name;
    public String surname;
    public int age;

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public User(){
        // no-arg constructor
    }
}