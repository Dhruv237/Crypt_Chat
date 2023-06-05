package com.example.communitychatapp.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.communitychatapp.ModelClass.Users;
import com.example.communitychatapp.R;
import com.example.communitychatapp.chat;
import com.squareup.picasso.Picasso;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder>{
    Context home;
    ArrayList<Users> usersArrayList;
    public UserAdapter(com.example.communitychatapp.Activity.home home, ArrayList<Users> usersArrayList){
        this.home=home;
        this.usersArrayList=usersArrayList;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view=LayoutInflater.from(home).inflate(R.layout.activity_item_user_row,parent,false);
        return new Viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder ,int position){
        Users users=usersArrayList.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(users.getUid()))
        {
            holder.itemView.setVisibility(View.GONE);
        }
        holder.user_name.setText(users.getName());
        holder.user_status.setText(users.getStatus());
        Picasso.get().load(users.getImageuri()).into(holder.user_profile);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle extras=new Bundle();
            Intent intent=new Intent(home, chat.class);
            extras.putString("name",users.getName());
            extras.putString("receiverImage",users.getImageuri());
            extras.putString("uid",users.getUid());
            intent.putExtras(extras);
            home.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount(){
        return usersArrayList.size();
    }
    class Viewholder extends RecyclerView.ViewHolder{
        CircleImageView user_profile;
        TextView user_name;
        TextView user_status;
        public Viewholder(@NonNull View itemView){

            super(itemView);
            user_profile=itemView.findViewById(R.id.profile_image);
            user_name=itemView.findViewById(R.id.user_name);
            user_status=itemView.findViewById(R.id.user_status);

        }
    }

}
