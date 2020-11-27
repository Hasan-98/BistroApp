package com.hasan.bistroapp.adopter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.hasan.bistroapp.Model.Users;
import com.hasan.bistroapp.R;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class UserAdopter extends RecyclerView.Adapter<UserAdopter.ViewHolder> {
    private Context context;
    private List<Users> mUsers;


    public UserAdopter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,
                parent,
                false);
        return new UserAdopter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Users users = mUsers.get(position);
            holder.username.setText(users.getUsername());
            if (users.getImageURL().equals("dafaults")){
                holder.imageView.setImageResource(R.mipmap.ic_launcher);
            }

    }

    public UserAdopter (Context context , List<Users> mUsers){
        this.context = context;
        this.mUsers = mUsers;
    }


    @Override
    public int getItemCount() {
        return mUsers.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username ;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
