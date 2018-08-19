package com.pmvb.minitumblr.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.pmvb.minitumblr.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postList = findViewById(R.id.post_list);
    }
}
