package com.pmvb.minitumblr;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pmvb.minitumblr.tasks.GetPostsTask;
import com.pmvb.minitumblr.adapter.PostListAdapter;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Post;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements GetPostsTask.GetPostsCallback {

    private RecyclerView postListView;
    private List<Post> postList;
    private PostListAdapter postsAdapter;
    private String blogName = "hawkingsbird";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postListView = findViewById(R.id.post_list);
        postList = new ArrayList<>();
        postsAdapter = new PostListAdapter(postList);

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
        postList = posts;
        processPosts(posts);
    }

    private void processPosts(List<Post> posts) {
        postList.addAll(posts);
        postsAdapter.addPosts(posts);
//        Log.d("MainActivity", "Posts: " + postList.size());
//        for (Post post : postList.subList(0, Math.min(50, posts.size()))) {
//            String log = "";
//            switch (post.getType()) {
//                case "text": {
//                    TextPost textPost = (TextPost) post;
//                    log = "Title: " + textPost.getTitle() + " - Body: " + textPost.getBody();
//                    break;
//                }
//                case "photo": {
//                    PhotoPost photoPost = (PhotoPost) post;
//                    log = "Caption: " + photoPost.getCaption() + " - Photos: " + photoPost.getPhotos().size();
//                    break;
//                }
//            }
//            Log.d("MainActivity", "Type: " + post.getType() + " - URL: " + post.getShortUrl() + " - Log: " + log);
//        }
    }

    @Override
    public void onPostsError(@NonNull Throwable error) {
        Log.e("MainActivity", error.getMessage());
        error.printStackTrace();
    }
}
