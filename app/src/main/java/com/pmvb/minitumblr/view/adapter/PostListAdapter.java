package com.pmvb.minitumblr.view.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pmvb.minitumblr.R;
import com.tumblr.jumblr.types.PhotoPost;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.TextPost;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    public enum PostType {
        TEXT,
        PHOTO
    }

    private List<Post> postList = new ArrayList<>();

    public PostListAdapter(List<Post> posts) {
        this.postList.addAll(posts);
    }

    public void addPosts(Collection<Post> posts) {
        int postCount = postList.size();
        postList.addAll(posts);
        notifyDataSetChanged();
    }

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
        PostType[] postTypes = PostType.values();
        switch (postTypes[viewType]) {
            case TEXT: {
                itemView = inflater.inflate(R.layout.post_item_text, parent, false);
                holder = new TextPostViewHolder(itemView);
                break;
            }
            case PHOTO: {
                itemView = inflater.inflate(R.layout.post_item_image, parent, false);
                holder = new PhotoPostViewHolder(itemView);
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
        String upperType = postList.get(position).getType().toUpperCase();
        try {
            return PostType.valueOf(upperType).ordinal();
        } catch (IllegalArgumentException ex) {
            return 1000;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(postList.get(position));
    }

    @Override
    public int getItemCount() {
        return postList.size();
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
            authorText.setText(post.getBlogName());
        }

        protected Spanned renderHtml(String text) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                return Html.fromHtml(text);
            } else {
                return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
            }
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
            contentText.setText(renderHtml(textPost.getBody()));
        }
    }

    static class PhotoPostViewHolder extends PostViewHolder {

        ImageView contentImage;
        TextView captionText;

        public PhotoPostViewHolder(View itemView) {
            super(itemView);
            contentImage = itemView.findViewById(R.id.post_content_image);
            captionText = itemView.findViewById(R.id.post_content_caption);
        }

        @Override
        void bind(Post post) {
            super.bind(post);
            PhotoPost imgPost = (PhotoPost) post;
            //TODO: Picasso load image
            captionText.setText(renderHtml(imgPost.getCaption()));
        }
    }
}
