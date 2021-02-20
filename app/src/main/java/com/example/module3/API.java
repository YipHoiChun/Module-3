package com.example.module3;

public class API {
    static String url = "https://reqres.in/api/";

    static String loginString() {
        return url + "login";
    }
    static String registerString() {
        return url + "register";
    }
}