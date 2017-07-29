package com.hazem.photpapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hazem.photpapp.R;
import com.hazem.photpapp.model.dummyModel;

import java.util.List;

/**
 * Created by hazem_ on 12/8/2016.
 */

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.ViewHolder> {

    List<dummyModel> contents;
    Context mContext;


    public DummyAdapter(List<dummyModel> contents, Context mContext) {
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

    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View view) {
            super(view);

        }
    }

}