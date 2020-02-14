package com.example.a9ingforsports;

public class ListViewItem {

    private String titleStr;
    private String dateStr;
    private String post_num;    //글번호
    private String view_num;    //조회수

    public void setTitle(String title){
        titleStr = title ;
    }
    public void setDate(String date){
        dateStr = date ;
    }
    public void setPNum(String pnum){
        post_num = pnum ;
    }
    public void setVNum(String vnum){
        view_num = vnum ;
    }

    public String getTitle(){
        return this.titleStr;
    }
    public String getDate(){
        return this.dateStr;
    }
    public String getPNum(){
        return this.post_num;
    }
    public String getVNum(){
        return this.view_num;
    }

}