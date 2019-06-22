package com.nav.model;

import java.io.Serializable;
import java.util.Date;

public class LogEntry implements Serializable {

    private String id;
    private Date created;
    private String title;
    private String body;

    public LogEntry(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
        created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }
}
