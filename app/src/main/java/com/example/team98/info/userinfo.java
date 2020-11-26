package com.example.team98.info;

import java.util.Date;

public class userinfo {
    private String id;
    private String name;
    private String pnum;
    private String author;
    private String UNI;
    private String studentnum;
    private String pass;
    private String cdate;

    public userinfo(String id,String name, String pnum,int n,String cdate)
    {
        this.id = id;
        this.name = name;
        this.pnum = pnum;
        this.cdate = cdate;
        if(id.equals("0"))
        {
            this.author="관리자";
        }
        else
            this.author="뉴비";
        this.UNI="공과대학 소프트웨어학과";
        this.studentnum= "미기입";

        if(n==1) pass ="이메일 인증";
        else pass = "미인증";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUNI() {
        return UNI;
    }

    public void setUNI(String UNI) {
        this.UNI = UNI;
    }

    public String getStudentnum() {
        return studentnum;
    }

    public void setStudentnum(String studentnum) {
        this.studentnum = studentnum;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }
}

