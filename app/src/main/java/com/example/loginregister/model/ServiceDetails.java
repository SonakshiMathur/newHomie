package com.example.loginregister.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ServiceDetails {
    String serviceName;
    Bitmap numbering;
    int status;
    public ServiceDetails(String serviceName, Bitmap numbering){
        this.serviceName=serviceName;
        this.numbering=numbering;
        status=0;
    }
    public String getName(){
        return serviceName;
    }

    public Bitmap getNumbering() {
        return numbering;
    }

    public void setStatus(int s, Bitmap checked){
        status = s;
        numbering = checked;
    }

    public int getStatus(){
        return status;
    }
}
