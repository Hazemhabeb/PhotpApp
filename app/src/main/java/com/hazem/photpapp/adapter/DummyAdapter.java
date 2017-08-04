package com.hazem.photpapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hazem.photpapp.R;
import com.hazem.photpapp.model.Post;

import java.util.List;

/**
 * Created by hazem_ on 12/8/2016.
 */

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.ViewHolder> {

    List<Post> contents;
    Context mContext;


    public DummyAdapter(List<Post> contents, Context mContext) {
        this.contents = contents;
        this.mContext = mContext;
    }


    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;


        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.userNameTV.setText(contents.get(position).getUserName());
        holder.postDiscTV.setText(contents.get(position).getDisc());
        holder.numLikeTV.setText(contents.get(position).getLikes().size()+"  Likes");

        Glide.with(mContext).load(contents.get(position).getUserImage()).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.userImage);

        Glide.with(mContext).load(contents.get(position).getImage()).
                diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.postImage);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView userImage,postImage;
        TextView userNameTV,numLikeTV,postDiscTV;
        ViewHolder(View view) {
            super(view);
            userImage= (ImageView) view.findViewById(R.id.userImage);
            postImage= (ImageView) view.findViewById(R.id.postImage);

            userNameTV= (TextView) view.findViewById(R.id.usernameTV);
            numLikeTV= (TextView) view.findViewById(R.id.numLikeTV);
            postDiscTV= (TextView) view.findViewById(R.id.postDiscTV);
        }
    }

}