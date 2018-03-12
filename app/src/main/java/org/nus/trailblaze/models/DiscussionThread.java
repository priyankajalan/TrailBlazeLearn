package org.nus.trailblaze.models;

/**
 * Created by plasmashadow on 3/11/18.
 */
import java.util.List;

public class DiscussionThread {
    private String id;
    private List<Post> posts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
