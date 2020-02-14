package com.example.a9ingforsports;

public class Employee_User {
    public String id;
    public String passwd;
    public String name;
    public String contact;
    public String qname;
    public String qname2;
    public String qnum;
    public String address;
    public String type;
    public String email;
    public String favorite;

    public Employee_User(){}

    public Employee_User(String id, String passwd, String name, String contact, String address, String qname, String qname2, String qnum, String email, String favorite){
        this.id=id;
        this.passwd=passwd;
        this.name=name;
        this.contact=contact;
        this.address=address;
        this.qname=qname;
        this.qname2=qname2;
        this.qnum=qnum;
        this.email=email;
        this.favorite=favorite;
        type="개인";
    }
}