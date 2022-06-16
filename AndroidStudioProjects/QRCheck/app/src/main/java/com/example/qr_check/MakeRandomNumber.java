package com.example.qr_check;

public class MakeRandomNumber {
    MakeRandomNumber(String sub_code){
        this.subjectCode = sub_code;
    }
    public int leftLimit = 65;//this is ascii 'A'
    public int rightLimit = 90;//this is ascii 'Z'
    public int randomCodeSize = 12;
    public String subjectCode;//

    String randomCode = "";

    public void addString(char code){
        this.randomCode = this.randomCode + code;
    }
    public String getter(){
        return randomCode;
    }

}