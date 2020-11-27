package com.hasan.bistroapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hasan.bistroapp.Model.Users;
import com.hasan.bistroapp.R;
import com.hasan.bistroapp.adopter.UserAdopter;

import java.util.ArrayList;
import java.util.List;


public class UserFragments extends Fragment {
    private RecyclerView recyclerView;
    private UserAdopter userAdopter;
    private List<Users> mUsers;

    public UserFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_fragments , container , false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        ReadUsers();
        return view;

    }


    private void ReadUsers(){
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();

                for (DataSnapshot shot : snapshot.getChildren()){
                    Users user = shot.getValue(Users.class);
                    assert user != null;

                    if (!user.getId().equals(firebaseUser.getUid())){
                        mUsers.add(user);
                    }
                    userAdopter = new UserAdopter(getContext() , mUsers);
                    recyclerView.setAdapter(userAdopter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}