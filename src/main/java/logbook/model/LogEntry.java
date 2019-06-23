package logbook.model;

import org.thymeleaf.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class LogEntry implements Serializable {

    private final String id;
    private Date created;
    private Date updated;
    private String title;
    private String body;

    public LogEntry(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
        created = new Date();
        updated = new Date();
    }

    public void update(LogEntry logEntry) {
        setBody(logEntry.getBody());
        setTitle(logEntry.getTitle());
        updated = new Date();
    }

    public static LogEntry clone(LogEntry logEntry) {
        LogEntry clone = new LogEntry(logEntry.getId(), logEntry.getTitle(), logEntry.getBody());
        clone.setCreated(logEntry.getCreated());
        clone.setUpdated(logEntry.getUpdated());
        return clone;
    }


    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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

    public Date getUpdated() {
        return updated;
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

    public boolean isEmpty() {
        return StringUtils.isEmpty(body) && StringUtils.isEmpty(title);
    }
}
