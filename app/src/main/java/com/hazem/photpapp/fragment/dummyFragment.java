package com.hazem.photpapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.hazem.photpapp.R;
import com.hazem.photpapp.activity.AddPost;
import com.hazem.photpapp.adapter.DummyAdapter;
import com.hazem.photpapp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class dummyFragment extends Fragment{

    //init the views
    private RecyclerView.Adapter mAdapter;
    private List<Post> mContentItems = new ArrayList<>();

     private FloatingActionButton  fabAddPost;
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
//        dummy();

        getData();

        fabAddPost= (FloatingActionButton) view.findViewById(R.id.fabAddTrip);

        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddPost.class));
            }
        });
    }


//    private void dummy(){
//        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
//        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
//        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
//        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
//        mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());mContentItems.add(new dummyModel());
//    }


    /**
     * to get the data from the firebase
     */
    private void getData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("posts");
        Query query=myRef.orderByChild("time");
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        mContentItems.clear();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                progressDialog.hide();
                Post post=dataSnapshot.getValue(Post.class);
                mContentItems.add(0,post);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.hide();
            }
        });
    }
}
