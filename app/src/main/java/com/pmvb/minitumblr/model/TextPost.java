package com.pmvb.minitumblr.model;

import io.objectbox.annotation.Entity;

@Entity
public class TextPost extends Post {

    private String body;

    public TextPost(long id, String type, String blogName, String avatarUrl, String body) {
        super(id, type, blogName, avatarUrl);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
