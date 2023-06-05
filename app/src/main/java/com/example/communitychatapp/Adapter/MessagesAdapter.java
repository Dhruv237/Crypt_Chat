package com.example.communitychatapp.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import static com.example.communitychatapp.chat.sImage;
import static com.example.communitychatapp.chat.rImage;
import static com.example.communitychatapp.chat.imgpath;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.communitychatapp.ModelClass.Users;
import com.example.communitychatapp.R;
import com.example.communitychatapp.chat;
import com.squareup.picasso.Picasso;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import com.example.communitychatapp.ModelClass.Messages;
import com.example.communitychatapp.ModelClass.Images;

import com.example.communitychatapp.R;

public class MessagesAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<Messages> messagesArrayList;
    ArrayList<Images> urls;
    int item_send=1;
    int item_receive=2;

    public MessagesAdapter(Context context,ArrayList<Messages> messagesArrayList){
        this.context=context;
        this.messagesArrayList=messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if(viewType==item_send){
            View view=LayoutInflater.from(context).inflate(R.layout.activity_sender_layout_item,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view=LayoutInflater.from(context).inflate(R.layout.activity_receiver_layout_item,parent,false);
            return new ReceiverViewHolder(view);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder , int position){
        Messages messages=messagesArrayList.get(position);
       // Images images=urls.get(position);
        if(holder.getClass()==SenderViewHolder.class){
          SenderViewHolder viewHolder=(SenderViewHolder)holder;
          viewHolder.txtmessage.setText(messages.getMsg());
           // viewHolder.txtmessage.setText("");
            Picasso.get().load(sImage).into(viewHolder.circleImageView);
            //if(viewHolder.txtmessage.getText().length()<0){
               // images.setImg(imgpath.toString());
             //   Picasso.get().load(images.getImg()).into(viewHolder.image);
            //}
            //else{
              //  viewHolder.txtmessage.setText(messages.getMsg());
            //}
          Picasso.get().load(sImage).into(viewHolder.circleImageView);
       //  Picasso.get().load(imgpath).into(viewHolder.image);
        }
        else{
            ReceiverViewHolder viewHolder=(ReceiverViewHolder)holder;
            viewHolder.txtmessage.setText(messages.getMsg());
           // viewHolder.txtmessage.setText("");
            Picasso.get().load(rImage).into(viewHolder.circleImageView);
          //  Picasso.get().load(images.getImg()).into(viewHolder.image1);


        }
    }
    @Override
    public int getItemCount(){
        return messagesArrayList.size();
    }
    @Override
    public int getItemViewType(int position){
     Messages messages=messagesArrayList.get(position);
     if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSender_id()))
     {
         return item_send;
     }
     else
     {
         return item_receive;
     }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView txtmessage;
        ImageView image;
        public SenderViewHolder(@NonNull View itemView){
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profile_image);
            txtmessage=itemView.findViewById(R.id.send_messages);
            image=itemView.findViewById(R.id.send_img);

        }
    }
    class ReceiverViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView txtmessage;
        ImageView image1;
        public ReceiverViewHolder(@NonNull View itemView){
            super(itemView);
            circleImageView=itemView.findViewById(R.id.profile_image);
            txtmessage=itemView.findViewById(R.id.get_messages);
            image1=itemView.findViewById(R.id.receive_img);
        }
    }
}
