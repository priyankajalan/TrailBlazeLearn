package org.nus.trailblaze.models;

/**
 * Created by plasmashadow on 3/11/18.
 */
import java.util.Date;

public class Item {
    private String id;
    private User user;
    private Date createdDate;
    private File file;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
