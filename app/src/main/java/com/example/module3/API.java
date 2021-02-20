package com.example.module3;

public class API {
    static String url = "https://wshk1920.herokuapp.com/api/";
//    static String url = "https://reqres.in/api/";


    static String loginString() {
        return url + "login";
    }
    static String registerString() {
        return url + "register";
    }

//    static String listUsers() { return url + "users"; };
//    static String listUser(int id) { return url + "users/" + id; }
    static String listUsers() { return url + "pets"; };
    static String listUser(int id) { return url + "pets/" + id; }
}