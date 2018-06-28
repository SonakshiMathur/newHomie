package com.example.loginregister.helpers;

public class URLs {

    private static final String ROOT_URL = "http://homieservices.com/Api.php?apicall=";
    private static final String ROOT_URL2 = "http://homieservices.com/Apii.php?apicall=";

    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_IMAGE_UPLOAD= ROOT_URL + "saveImages";
    public static final String URL_IMAGE_ID_DOWNLOAD= ROOT_URL + "retImages";
    public static final String URL_IMAGE_DIWNLOAD= ROOT_URL + "getImage";
    public static final String URL_ProPic_DOWNLOAD= ROOT_URL + "getpropic";
    public static final String URL_ProPic_UPLOAD= ROOT_URL + "savepropic";

    public static final String URL_REGISTER2 = ROOT_URL2 + "signup";
    public static final String URL_LOGIN2= ROOT_URL2 + "login";
}
