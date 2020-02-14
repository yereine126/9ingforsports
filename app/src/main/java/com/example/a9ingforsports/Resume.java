package com.example.a9ingforsports;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Resume {
    public String id;
    public String title;
    public String introduction;

    public Resume(){

    }

    public Resume(String id, String title, String introduction){
        this.id = id;
        this.title = title;
        this.introduction = introduction;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("title", title);
        result.put("introduction", introduction);

        return result;
    }
}