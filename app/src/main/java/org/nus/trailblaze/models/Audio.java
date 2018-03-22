package org.nus.trailblaze.models;

import java.util.Date;

/**
 * Created by plasmashadow on 3/11/18.
 */

public class Audio extends File{
    public Audio() {}
    public Audio(String id, String name, String url, Float size, Date uploadDate, String mimeType) {
        super(id, name, url, size, uploadDate, mimeType);
    }
}
