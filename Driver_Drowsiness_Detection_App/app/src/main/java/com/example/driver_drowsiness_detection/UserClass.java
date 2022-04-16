package com.example.driver_drowsiness_detection;

public class UserClass {
    private String fName,lName,pNum,emerNum,vehNum, id;

    public UserClass()
    {
        this.fName = "";
        this.lName = "";
        this.pNum = "";
        this.emerNum = "";
        this.vehNum = "";
        this.id = "";
    }

    public UserClass(String fName, String lName, String pNum, String emerNum, String vehNum, String id)
    {
        this.fName = fName;
        this.lName = lName;
        this.pNum = pNum;
        this.emerNum = emerNum;
        this.vehNum = vehNum;
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getEmerNum() {
        return emerNum;
    }

    public void setEmerNum(String emerNum) {
        this.emerNum = emerNum;
    }

    public String getVehNum() {
        return vehNum;
    }

    public void setVehNum(String vehNum) {
        this.vehNum = vehNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
