package com.hasan.bistroapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hasan.bistroapp.Model.Users;
import com.hasan.bistroapp.fragments.ChatsFragments;
import com.hasan.bistroapp.fragments.UserFragments;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("User").child(
            firebaseUser.getUid());

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Users users = snapshot.getValue(Users.class);
               //  Toast.makeText(MainActivity.this , "User Login" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TabLayout tableLayout = findViewById(R.id.tabLayout);
        ViewPager  viewPager = findViewById(R.id.view_pager);
        ViewPagerAdopter viewPagerAdopter = new ViewPagerAdopter(getSupportFragmentManager());
        viewPagerAdopter.addFragment(new ChatsFragments(),"Chats" ) ;
        viewPagerAdopter.addFragment(new UserFragments(),"Users" ) ;

        viewPager.setAdapter(viewPagerAdopter);
        tableLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this , Login_Activity.class));
                finish();
                return true;
        }
        return false;
    }

    class ViewPagerAdopter extends FragmentPagerAdapter{
        private ArrayList <Fragment> fragments;
        private ArrayList <String> titles;

        ViewPagerAdopter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles =  new ArrayList<>();
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment , String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return titles.get(position);
        }
    }

}
