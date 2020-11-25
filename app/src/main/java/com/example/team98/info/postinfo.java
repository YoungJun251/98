package com.example.team98.info;

import java.util.Date;

public class postinfo {
    private String title;
    private String contents;
    private String publisher;
    private String uri;
    private Date createdate;
    private String id;




    public postinfo(String title, String Contents, String publisher, String uri, Date createdate)
    {
        this.title = title;
        this.contents = Contents;
        this.publisher = publisher;
        this.uri = uri;
        this.createdate = createdate;
        this.id = id;
    }
    public String getUri() {
        return uri;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
