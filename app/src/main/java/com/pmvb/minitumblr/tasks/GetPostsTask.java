package com.pmvb.minitumblr.tasks;


import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.pmvb.minitumblr.model.PhotoPost;
import com.pmvb.minitumblr.model.Post;
import com.pmvb.minitumblr.model.TextPost;
import com.pmvb.minitumblr.store.Store;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Photo;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class GetPostsTask extends AsyncTask<String, Void, Void> {
    private final GetPostsCallback callback;
    private JumblrClient jumblr;
    private List<Post> posts;

    public GetPostsTask(JumblrClient jumblr, GetPostsCallback callback) {
        this.jumblr = jumblr;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(String... strings) {
        if (strings.length == 0) {
            callback.onPostsError(new IllegalArgumentException("Missing argument: blogName"));
        }
        try {
            String blogName = strings[0];
            List<com.tumblr.jumblr.types.Post> postList = jumblr.blogPosts(blogName);
            this.posts = processPosts(postList);
            persistPosts(posts);
        } catch (JumblrException jumblrEx) {
            callback.onPostsError(jumblrEx);
        } catch (Exception ex) {
            callback.onPostsError(ex);
        }
        return null;
    }

    private void persistPosts(List<Post> posts) {
        BoxStore boxStore = Store.getInstance().getBoxStore();
        Box<TextPost> textPostBox = boxStore.boxFor(TextPost.class);
        Box<PhotoPost> photoPostBox = boxStore.boxFor(PhotoPost.class);
        for (Post post : posts) {
            switch (post.getType()) {
                case "text": {
                    textPostBox.put((TextPost) post);
                    break;
                }
                case "photo": {
                    photoPostBox.put((PhotoPost) post);
                    break;
                }
            }
        }
    }

    private List<Post> processPosts(List<com.tumblr.jumblr.types.Post> postList) {
        List<Post> posts = new ArrayList<>();
        for (com.tumblr.jumblr.types.Post post : postList) {
            Post newPost = null;
            switch (post.getType()) {
                case "text": {
                    newPost = new TextPost(
                            post.getId(), post.getType(), post.getBlogName(), "",
                            ((com.tumblr.jumblr.types.TextPost) post).getBody());
                    break;
                }
                case "photo": {
                    com.tumblr.jumblr.types.PhotoPost photoPost = (com.tumblr.jumblr.types.PhotoPost) post;
                    List<String> photoUrls = new ArrayList<>();
                    for (Photo photo : photoPost.getPhotos()) {
                        photoUrls.add(photo.getOriginalSize().getUrl());
                    }
                    newPost = new PhotoPost(
                            post.getId(), post.getType(), post.getBlogName(), "",
                            photoPost.getCaption(), photoUrls);
                    break;
                }
            }
            if (newPost != null) {
                posts.add(newPost);
            }
        }
        return posts;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (posts != null) {
            callback.onPostsSuccess(posts);
        }
    }

    public interface GetPostsCallback {
        void onPostsSuccess(@NonNull List<Post> posts);
        void onPostsError(@NonNull Throwable error);
    }
}
