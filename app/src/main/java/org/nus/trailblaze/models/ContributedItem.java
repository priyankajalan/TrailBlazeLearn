package org.nus.trailblaze.models;

import android.media.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by plasmashadow on 3/11/18.
 */

public class ContributedItem extends Item {
    private String description;

    public ContributedItem(String id, User user, Date createdDate, File file, String description) {
        super(id, user, createdDate, file);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

