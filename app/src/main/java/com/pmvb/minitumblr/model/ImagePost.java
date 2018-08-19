package com.pmvb.minitumblr.model;

public class ImagePost extends Post {

    private String caption;

    public ImagePost(PostType type) {
        super(type);
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
