package com.pmvb.minitumblr.view;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pmvb.minitumblr.R;
import com.pmvb.minitumblr.tasks.GetPostsTask;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements GetPostsTask.GetPostsCallback {

    private RecyclerView postListView;
    private JumblrClient jumblr;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jumblr = new JumblrClient(
                getString(R.string.tumblr_api_consumer_key),
                getString(R.string.tumblr_api_consumer_secret)
        );
        postList = new ArrayList<>();
        GetPostsTask task = new GetPostsTask(jumblr, this);
        task.execute("hawkingsbird");
        postListView = findViewById(R.id.post_list);
    }

    @Override
    public void onPostsSuccess(@NonNull List<Post> posts) {
        postList = posts;
        processPosts(posts);
    }

    private void processPosts(List<Post> posts) {
        postList.addAll(posts);
        Log.d("MainActivity", "Posts: " + postList.size());
        for (Post post : postList.subList(0, Math.min(50, posts.size()))) {
            String log = "";
            switch (post.getType()) {
                case "text": {
                    TextPost textPost = (TextPost) post;
                    log = "Title: " + textPost.getTitle() + " - Body: " + textPost.getBody();
                    break;
                }
                case "photo": {
                    PhotoPost photoPost = (PhotoPost) post;
                    log = "Caption: " + photoPost.getCaption() + " - Photos: " + photoPost.getPhotos().size();
                    break;
                }
            }
            Log.d("MainActivity", "Type: " + post.getType() + " - URL: " + post.getShortUrl() + " - Log: " + log);
        }
    }

    @Override
    public void onPostsError(@NonNull Throwable error) {
        Log.e("MainActivity", error.getMessage());
        error.printStackTrace();
    }
}
