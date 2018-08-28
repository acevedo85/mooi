package com.example.jorgeacevedo.mooi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
//import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {

    private RecyclerView postList;
    private FloatingActionButton AddNewPostButton;
    FirebaseRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseReference UserRef, PostRef;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        AddNewPostButton = (FloatingActionButton) view.findViewById(R.id.fab);

        postList = (RecyclerView) view.findViewById(R.id.all_users_post_list);
        postList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        postList.setLayoutManager(mLayoutManager);

        AddNewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToPostActivity();
            }
        });


       DisplayAllUsersPosts();
        return view;
    }

    private void SendUserToPostActivity() {
        Intent addNewPostIntent =new Intent(getActivity(),PostActivity.class);
        startActivity(addNewPostIntent);
    }

    private void DisplayAllUsersPosts() {

        FirebaseRecyclerOptions<Posts> options =
                new FirebaseRecyclerOptions.Builder<Posts>()
                        .setQuery(PostRef, Posts.class)
                        .build();
        mAdapter = new FirebaseRecyclerAdapter<Posts, MainActivity.PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MainActivity.PostViewHolder holder, int position, @NonNull Posts model) {

                final String PostKey = getRef(position).getKey();

                holder.setFullname(model.getFullname());
                holder.setDate(model.getDate());
                holder.setDescription(model.getDescription());
                holder.setTime(model.getTime());
                holder.setPostimage(getActivity(), model.getPostimage());
                holder.setProfileimage(getActivity(), model.getProfileimage());

                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent clickPostIntent = new Intent(getActivity(), ClickPostActivity.class);
                        clickPostIntent.putExtra("PostKey", PostKey);
                        startActivity(clickPostIntent);
                    }
                });
            }

            @NonNull
            @Override
            public MainActivity.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_posts_layout, parent, false);


                return new MainActivity.PostViewHolder(view);
            }
        };
        postList.setAdapter(mAdapter);
    }


    @Override
    public void onStart() {
        mAdapter.startListening();
        super.onStart();
        //adapter.startListening();
    }
}
