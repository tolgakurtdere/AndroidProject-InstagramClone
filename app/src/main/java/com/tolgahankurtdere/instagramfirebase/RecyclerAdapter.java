package com.tolgahankurtdere.instagramfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostHolder> {

    private ArrayList<String> userEmailList,userImageList,userCommentList;

    public RecyclerAdapter(ArrayList<String> userEmailList, ArrayList<String> userImageList, ArrayList<String> userCommentList) {
        this.userEmailList = userEmailList;
        this.userImageList = userImageList;
        this.userCommentList = userCommentList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //connect with recycler_row.xml
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) { //what is gonna happen after connection
        holder.textView_userEmail.setText(userEmailList.get(position));
        holder.textView_comment.setText(userCommentList.get(position));
        Picasso.get().load(userImageList.get(position)).into(holder.imageView); //get and show photos thanks to Picasso library
    }

    @Override
    public int getItemCount() { //kac tane row olacak?
        return userImageList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_userEmail;
        TextView textView_comment;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_recyclerview);
            textView_userEmail = itemView.findViewById(R.id.textView_recyclerview_userEmail);
            textView_comment = itemView.findViewById(R.id.textView_recyclerview_comment);
        }
    }
}
