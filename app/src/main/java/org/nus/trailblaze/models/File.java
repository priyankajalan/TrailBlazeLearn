package org.nus.trailblaze.models;

/**
 * Created by plasmashadow on 3/11/18.
 */
import java.util.Date;

public class File {
    private String id;
    private String name;
    private String url;
    private Float size;
    private Date uploadDate;
    private String mimeType;

    public File(String id, String name, String url, Float size, Date uploadDate, String mimeType) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.size = size;
        this.uploadDate = uploadDate;
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
}
