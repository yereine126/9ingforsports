package com.example.a9ingforsports;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String id;
    public String passwd;
    public String name;
    public String contact;
    public String bLicenseNum;
    public String address;
    public String type;
    public String email;
    public String favorite;
    public User(){}

    public User(String id, String passwd,String name, String contact,String bLicenseNum,String address,String email,String favorite){
        this.id=id;
        this.passwd=passwd;
        this.name=name;
        this.contact=contact;
        this.bLicenseNum=bLicenseNum;
        this.address=address;
        this.email=email;
        this.favorite=favorite;
        type="기업";


    }
}
