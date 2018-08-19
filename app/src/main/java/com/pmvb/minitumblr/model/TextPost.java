package com.pmvb.minitumblr.model;

public class TextPost extends Post {

    private String body;

    public TextPost(PostType type) {
        super(type);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
