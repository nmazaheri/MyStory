package logbook.model;

import org.thymeleaf.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("WeakerAccess")
public class LogEntry implements Serializable {

    private String id;
    private String title = "";
    private String body = "";
    private Date created = new Date();
    private Date updated = new Date();

    public LogEntry(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public LogEntry() {
        this.id = getUniqueId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(body) && StringUtils.isEmpty(title);
    }

    private static String getUniqueId() {
        return UUID.randomUUID().toString();
    }
}
