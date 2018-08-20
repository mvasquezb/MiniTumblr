package com.pmvb.minitumblr.model;

import java.util.List;

import io.objectbox.annotation.Entity;

@Entity
public class PhotoPost extends Post {
    private String caption;
    private String originalSizePhotoUrl;
    private transient List<String> photoUrls;

    public PhotoPost(long id, String type, String blogName, String avatarUrl, String caption, List<String> photoUrls) {
        super(id, type, blogName, avatarUrl);
        this.caption = caption;
        this.photoUrls = photoUrls;
        if (!photoUrls.isEmpty()) {
            this.originalSizePhotoUrl = photoUrls.get(0);
        }
    }

    public List<String> getPhotoUrls() {
        if (photoUrls.isEmpty() && originalSizePhotoUrl != null) {
            photoUrls.add(originalSizePhotoUrl);
        }
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getOriginalSizePhotoUrl() {
        return originalSizePhotoUrl;
    }

    public void setOriginalSizePhotoUrl(String originalSizePhotoUrl) {
        this.originalSizePhotoUrl = originalSizePhotoUrl;
    }
}
