package com.pmvb.minitumblr.model;

import io.objectbox.annotation.BaseEntity;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@BaseEntity
public class Post {

    @Id private long id;
    private String blogName;
    private String avatarUrl;
    private String type;

    public Post(long id, String type, String blogName, String avatarUrl) {
        this.id = id;
        this.type = type;
        this.blogName = blogName;
        this.avatarUrl = avatarUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
