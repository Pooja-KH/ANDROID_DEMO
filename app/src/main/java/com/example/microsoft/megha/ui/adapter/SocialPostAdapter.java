package com.example.microsoft.megha.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.microsoft.megha.R;
import com.example.microsoft.megha.model.PostModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/****************************************************
 * Created by Dharam Shinde on 22-12-2018
 * Infoz Solution
 * vivekshinde31121996@gmail.com
 *****************************************************/
public class SocialPostAdapter extends RecyclerView.Adapter<SocialPostAdapter.MyViewHolder> {
    List<PostModel> postModelList;
    Context context;

    public SocialPostAdapter(Context context, @NonNull List<PostModel> postModelList) {
        this.postModelList = postModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_for_item_view, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostModel postModel = postModelList.get(position);

        holder.UserNameTextVIew.setText(postModel.getFirstName() + " " + postModel.getLastName());
        holder.UserPostTextView.setText(postModel.getPost());
        Picasso.get().load("https://img.icons8.com/color/1600/circled-user-male-skin-type-1-2.png").into(holder.userImageView);

    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userImageView, likeImageView, commentImageView, shareImageView;
        TextView UserNameTextVIew, UserPostTextView;


        public MyViewHolder(View itemView) {
            super(itemView);
            userImageView = itemView.findViewById(R.id.userImageView);
            likeImageView = itemView.findViewById(R.id.likeImageView);
            commentImageView = itemView.findViewById(R.id.commentImageView);
            shareImageView = itemView.findViewById(R.id.shareImageView);

            UserNameTextVIew = itemView.findViewById(R.id.UserNameTextVIew);
            UserPostTextView = itemView.findViewById(R.id.UserPostTextView);

        }
    }
}
