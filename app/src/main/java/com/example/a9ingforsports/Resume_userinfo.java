package com.example.a9ingforsports;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Resume_userinfo {
    public String name;
    public String contact;
    public String address;
    public String email;
    public String career_place;
    public String career_data;
    public String career_place2;
    public String career_data2;

    public Resume_userinfo(){

    }

    public Resume_userinfo(String name, String contact, String address, String email, String career_place, String career_data,
                           String career_place2, String career_data2){
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
        this.career_place = career_place;
        this.career_data = career_data;
        this.career_place2 = career_place2;
        this.career_data2 = career_data2;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("contact", contact);
        result.put("address",address);
        result.put("email",email);
        result.put("career_place",career_place);
        result.put("career_data",career_data);
        result.put("career_place2",career_place2);
        result.put("career_data2",career_data2);

        return result;
    }
}