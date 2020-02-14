package com.example.a9ingforsports;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;


@IgnoreExtraProperties
public class joboffer {

    public String category;
    public String contents;
    public String end_date;
    public String id;
    public String period;
    public String start_date;
    public String title;
    public String address;
    public int vnum;

    public joboffer(){

    }

    public joboffer(String category, String contents, String end_date, String id, String period, String start_date, String title, String address, int vnum){

        this.category = category;
        this.contents = contents;
        this.end_date = end_date;
        this.id = id;
        this.period = period;
        this.start_date = start_date;
        this.title = title;
        this.address = address;
        this.vnum = vnum;

    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("category",category);
        result.put("contents",contents);
        result.put("end_date",end_date);
        result.put("id",id);
        result.put("period",period);
        result.put("start_date",start_date);
        result.put("title",title);
        result.put("address",address);
        result.put("vnum",vnum);

        return result;
    }


}
