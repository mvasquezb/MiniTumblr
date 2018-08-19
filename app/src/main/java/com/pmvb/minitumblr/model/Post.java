package com.pmvb.minitumblr.model;

public class Post {

    public enum PostType {
        TEXT,
        IMAGE,
        VIDEO
    }

    private String authorName;
    private String authorImgUrl;
    private PostType type;

    public Post(PostType type) {
        this.type = type;
    }

    public PostType getType() {
        return type;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImgUrl() {
        return authorImgUrl;
    }

    public void setAuthorImgUrl(String authorImgUrl) {
        this.authorImgUrl = authorImgUrl;
    }
}
