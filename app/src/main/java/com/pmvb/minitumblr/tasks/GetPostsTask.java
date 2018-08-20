package com.pmvb.minitumblr.tasks;


import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Post;

import java.util.List;

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
            this.posts = jumblr.blogPosts(blogName);
        } catch (JumblrException jumblrEx) {
            callback.onPostsError(jumblrEx);
        } catch (Exception ex) {
            callback.onPostsError(ex);
        }
        return null;
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
