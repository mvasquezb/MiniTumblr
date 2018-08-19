package com.pmvb.minitumblr.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmvb.minitumblr.R;
import com.pmvb.minitumblr.model.ImagePost;
import com.pmvb.minitumblr.model.Post;
import com.pmvb.minitumblr.model.TextPost;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private List<Post> postList;

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostViewHolder holder = mapPostToViewHolder(parent, viewType);
        return holder;
    }

    private PostViewHolder mapPostToViewHolder(ViewGroup parent, int viewType) {
        PostViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        Post.PostType[] postTypes = Post.PostType.values();
        switch (postTypes[viewType]) {
            case TEXT: {
                itemView = inflater.inflate(R.layout.post_item_text, parent, false);
                holder = new TextPostViewHolder(itemView);
                break;
            }
            case IMAGE: {
                itemView = inflater.inflate(R.layout.post_item_image, parent, false);
                holder = new ImagePostViewHolder(itemView);
                break;
            }
            default: {
                itemView = inflater.inflate(R.layout.post_item_header, parent, false);
                holder = new PostViewHolder(itemView);
                break;
            }
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return postList.get(position).getType().ordinal();
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView authorImg;
        TextView authorText;

        public PostViewHolder(View itemView) {
            super(itemView);
            authorImg = itemView.findViewById(R.id.post_author_img);
            authorText = itemView.findViewById(R.id.post_author_name);
        }

        void bind(Post post) {
            //TODO: Picasso load author image
            authorText.setText(post.getAuthorName());
        }
    }

    static class TextPostViewHolder extends PostViewHolder {

        TextView contentText;

        public TextPostViewHolder(View itemView) {
            super(itemView);
            contentText = itemView.findViewById(R.id.post_content_text);
        }

        @Override
        void bind(Post post) {
            super.bind(post);
            TextPost textPost = (TextPost) post;
            contentText.setText(textPost.getBody());
        }
    }

    static class ImagePostViewHolder extends PostViewHolder {

        ImageView contentImage;
        TextView captionText;

        public ImagePostViewHolder(View itemView) {
            super(itemView);
            contentImage = itemView.findViewById(R.id.post_content_image);
            captionText = itemView.findViewById(R.id.post_content_caption);
        }

        @Override
        void bind(Post post) {
            super.bind(post);
            ImagePost imgPost = (ImagePost) post;
            //TODO: Picasso load image
            captionText.setText(imgPost.getCaption());
        }
    }
}
