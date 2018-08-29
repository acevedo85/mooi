package com.example.jorgeacevedo.mooi;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private BusinessFragment businessFragment;
    private MenuFragment menuFragment;
    private MapFragment mapFragment;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView postList;
    private Toolbar mToolbar;

    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;
   // private ImageButton AddNewPostButton;
    private FloatingActionButton AddNewPostFloatingButton;

    private FirebaseAuth mAuth;
    private DatabaseReference UserRef, PostRef;


    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.main_nav);

        homeFragment = new HomeFragment();
        notificationFragment = new NotificationFragment();
        businessFragment = new BusinessFragment();
        menuFragment = new MenuFragment();
        mapFragment = new MapFragment();


        setFragment(homeFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                       // mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(homeFragment);
                        return true;
                    case R.id.nav_notification:
                       // mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(notificationFragment);
                        return true;
                    case R.id.nav_business:
                       // mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(businessFragment);
                        return  true;
                    case R.id.nav_menu:
                       // mMainNav.setItemBackgroundResource(R.color.colorWhite);
                        setFragment(menuFragment);
                    case R.id.nav_map:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(mapFragment);

                        default:
                            return false;
                }
            }
        });








        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");
/*
       // AddNewPostButton = (ImageButton) findViewById(R.id.add_new_post_button);

       // AddNewPostFloatingButton = (FloatingActionButton) findViewById(R.id.fab);

       // drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
       // actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_closed);
       // drawerLayout.addDrawerListener(actionBarDrawerToggle);
       // actionBarDrawerToggle.syncState();
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //postList = (RecyclerView) findViewById(R.id.all_users_post_list);
       *//* postList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postList.setLayoutManager(linearLayoutManager);*//*

        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_user_full_name);


        UserRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    if(dataSnapshot.hasChild("fullname")){
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        NavProfileUserName.setText(fullname);
                    }

                    if (dataSnapshot.hasChild("profileimage")){
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);
                    }else{
                        Toast.makeText(MainActivity.this, "Profile name do not exists", Toast.LENGTH_SHORT).show();
                    }



                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                UserMenuSelector (item);

                return false;
            }
        });

       *//* AddNewPostFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendUserToPostActivity();
            }
        });*//*

        DisplayAllUsersPosts();*/
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    /*private void DisplayAllUsersPosts() {

        FirebaseRecyclerOptions<Posts> options =
                new FirebaseRecyclerOptions.Builder<Posts>()
                .setQuery(PostRef, Posts.class)
                .build();

        *//*FirebaseRecyclerOptions<Posts> firebaseRecyclerOptions =new FirebaseRecyclerOptions.Builder<Posts>
                ().setQuery(PostRef,Posts.class)
                .build();*//*

        adapter = new FirebaseRecyclerAdapter<Posts, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Posts model) {
                holder.setFullname(model.getFullname());
                holder.setDate(model.getDate());
                holder.setDescription(model.getDescription());
                holder.setTime(model.getTime());
                holder.setPostimage(getApplicationContext(), model.getPostimage());
                holder.setProfileimage(getApplicationContext(), model.getProfileimage());
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.all_posts_layout, parent, false);


                return new PostViewHolder(view);
            }
        };


*//*        adapter = new FirebaseRecyclerAdapter<Posts, PostsHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PostsHolder holder, int position, @NonNull Posts model) {
                holder.setPosts(model);

            }

            @NonNull
            @Override
            public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.all_posts_layout,parent,false);

                return  new PostsHolder(view);
            }
        };*//*
        postList.setAdapter(adapter);

    }



  *//*  private void DisplayAllUsersPosts() {
        FirebaseRecyclerOptions<Posts> firebaseRecyclerOptions =new FirebaseRecyclerOptions.Builder<Posts>
                ().setQuery(PostRef,Posts.class)
                .build();


        FirebaseRecyclerAdapter<Posts, PostViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Posts, PostViewHolder>(firebaseRecyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Posts model) {
                holder.setFullname(model.getFullname());
                holder.setDate(model.getDate());
                holder.setDescription(model.getDescription());
                holder.setTime(model.getTime());
                holder.setPostimage(getApplicationContext(), model.getPostimage());
                holder.setProfileimage(getApplicationContext(), model.getProfileimage());


            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };

        postList.setAdapter(firebaseRecyclerAdapter);

    }*//*
*/


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public PostViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }


        public void setFullname(String fullname) {

           TextView username = (TextView) mView.findViewById(R.id.post_user_name);
           username.setText(fullname);
        }

        public void setProfileimage(Context ctx, String profileimage){
            CircleImageView image = (CircleImageView) mView.findViewById(R.id.post_profile_image);
            Picasso.get().load(profileimage).into(image);
        }

        public void setTime(String time){
            TextView PostTime = (TextView) mView.findViewById(R.id.post_time);
            PostTime.setText("    " + time);
        }

        public void setDate(String date){
            TextView PostDate = (TextView) mView.findViewById(R.id.post_date);
            PostDate.setText("     " + date);
        }

        public void setDescription(String description){
            TextView PostDescription = (TextView) mView.findViewById(R.id.post_description);
            PostDescription.setText(description);
        }

        public void setPostimage(Context ctx, String postimage){
            ImageView PostImage = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.get().load(postimage).into(PostImage);
        }
    };

   /* public static class PostsHolder extends RecyclerView.ViewHolder {

        private CircleImageView profile_image;
        private ImageView post_image;
        private TextView Description;
        private TextView Date,Time, Has_Updated_Post,Has_Username;


        public PostsHolder(View itemView) {
            super(itemView);

            Date=itemView.findViewById(R.id.post_date);
            Time=itemView.findViewById(R.id.post_time);
            *//*Has_Updated_Post=itemView.findViewById(R.id.text);*//*
            Has_Username=itemView.findViewById(R.id.post_user_name);
            Description=itemView.findViewById(R.id.post_description);
            profile_image=itemView.findViewById(R.id.post_profile_image);
            post_image=itemView.findViewById(R.id.post_image);
        }

        public  void setPosts(Posts posts){

            String users_name=posts.getFullname();
            Has_Username.setText(users_name);
            String users_description=posts.getDescription();
            Description.setText(users_description);
            String users_date=posts.getDate();
            Date.setText(" " +users_date);
            String users_time=posts.getTime();
            Time.setText("" +users_time);
            String users_image=posts.getProfileimage();
            Picasso.get().load(users_image).into(profile_image);
            String users_posts_image=posts.getPostimage();
            Picasso.get().load(users_posts_image).into(post_image);



        }



    }*/

    public void SendUserToPostActivity() {
        Intent addNewPostIntent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(addNewPostIntent);
    }




    @Override
    protected void onStart() {
        super.onStart();

        //adapter.startListening();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
        {
            SendUserToLoginActivity();
        }else {
            CheckUserExistance();
        }


    }


    private void CheckUserExistance() {

        final String current_user_id = mAuth.getCurrentUser().getUid();

        Log.d("current user data","Provider: "+mAuth.getCurrentUser().getProviderId()+" id: "+current_user_id+" email: "+mAuth.getCurrentUser().getEmail());
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(current_user_id)){
                    SendUserToSetupActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void SendUserToSetupActivity() {
        Intent setupIntent = new Intent(MainActivity.this, SetupActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_post:
                SendUserToPostActivity();
                break;

            case R.id.nav_profile:
                Toast.makeText(this,"Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_home:
                Toast.makeText(this,"Home", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_friends:
                Toast.makeText(this,"Friends List", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_find_friends:
                Toast.makeText(this,"Find Friends", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_messages:
                Toast.makeText(this,"Messages", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_settings:
                Toast.makeText(this,"Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                mAuth.signOut();
                SendUserToLoginActivity();
                break;
        }
    }
}
