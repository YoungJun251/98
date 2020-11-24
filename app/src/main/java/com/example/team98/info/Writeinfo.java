package com.example.team98.info;

public class Writeinfo {
    private String title;
    private String contents;
    private String publisher;



    public Writeinfo(String title, String Contents, String publisher)
    {
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
    }
    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
