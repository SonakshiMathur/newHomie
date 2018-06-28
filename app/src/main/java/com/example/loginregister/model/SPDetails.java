package com.example.loginregister.model;

public class SPDetails {

    private String name;
    private int id;
    private String email;
    private String phoneNumber;
    private String password;
    private String award, idproof, references, work, ratechart, proPic;

    public SPDetails(int id,String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public SPDetails(int id,String name, String email, String phoneNumber,String award,String idproof,String references,String work,String ratechart, String proPic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.award=award;
        this.idproof=idproof;
        this.references=references;
        this.work=work;
        this.ratechart=ratechart;
        this.proPic = proPic;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) { this.email = email; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAward(String award) {
        this.award= award;
    }

    public void setIdproof(String Idproof) {
        this.idproof = Idproof;
    }

    public void setReferences(String references) {
        this.references = references;
    }


    public void setWorking(String work) {
        this.work = work;
    }

    public void setRatechart(String ratechart) {
        this.ratechart = ratechart;
    }
    public void setProPic(String proPic) {
        this.proPic = proPic;
    }
    public String getProPic() {
        return proPic;
    }
    public String getAward() {
        return award;
    }

    public String getIdproof() {
        return idproof;
    }

    public String getReferences() {
        return references;
    }

    public String getWorking() {
        return work;
    }

    public String getRatechart() {
        return ratechart;
    }



}
