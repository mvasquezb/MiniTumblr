package com.pmvb.minitumblr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pmvb.minitumblr.adapter.PostListAdapter;
import com.pmvb.minitumblr.model.Post;
import com.pmvb.minitumblr.store.Store;
import com.pmvb.minitumblr.tasks.GetPostsTask;
import com.tumblr.jumblr.JumblrClient;

import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements GetPostsTask.GetPostsCallback {

    private RecyclerView postListView;
    private PostListAdapter postsAdapter;
    private String blogName = "hawkingsbird";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Store.create(this);
        postListView = findViewById(R.id.post_list);
        postsAdapter = new PostListAdapter();

        JumblrClient jumblr = new JumblrClient(
                getString(R.string.tumblr_api_consumer_key),
                getString(R.string.tumblr_api_consumer_secret)
        );
        postListView.setLayoutManager(new LinearLayoutManager(this));
        postListView.setAdapter(postsAdapter);

        GetPostsTask task = new GetPostsTask(jumblr, this);
        task.execute(blogName);
    }

    @Override
    public void onPostsSuccess(@NonNull List<Post> posts) {
        processPosts(posts);
    }

    private void processPosts(List<Post> posts) {
        postsAdapter.addPosts(posts);
    }

    @Override
    public void onPostsError(@NonNull Throwable error) {
        Log.e("MainActivity", error.getMessage());
        error.printStackTrace();
    }
}
