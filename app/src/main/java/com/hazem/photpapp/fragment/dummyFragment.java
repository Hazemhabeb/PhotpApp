package com.hazem.photpapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hazem.photpapp.R;
import com.hazem.photpapp.adapter.DummyAdapter;
import com.hazem.photpapp.model.dummyModel;

import java.util.ArrayList;
import java.util.List;

public class dummyFragment extends Fragment{

    private RecyclerView.Adapter mAdapter;

    private List<dummyModel> mContentItems = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dummy_fragment, container, false);
        initViews(view);
        return view;
    }

    public void initViews(View view) {
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvCards);
        RecyclerView.LayoutManager
                layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DummyAdapter(mContentItems, getActivity());
        recyclerView.setAdapter(mAdapter);
        dummy();
    }
    private void dummy(){
        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
    }

}
