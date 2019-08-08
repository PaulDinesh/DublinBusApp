package com.example.dublinbusapp;

public class StopInformation {

    private String stopid;
    private String shortname;
    private String fullname;

    public String getstopid() {
        return stopid;
    }
    public void setstopid(String Stopid) {
        stopid = Stopid;
    }
    public String getshortname() {
        return shortname;
    }
    public void setshortname(String Shortname) {
        shortname = Shortname;
    }
    public String getfullname() {
        return fullname;
    }
    public void setfullname(String Fullname) {
        fullname = Fullname;
    }
}