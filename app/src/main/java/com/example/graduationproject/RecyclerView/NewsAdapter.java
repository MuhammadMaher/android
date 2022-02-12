package com.example.graduationproject.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationproject.R;
import com.example.graduationproject.api.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.Holder> {
    private static final String TAG = "NewsAdapter";
    List<Article> list;
    Context context;

    public NewsAdapter(List<Article> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
     Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  NewsAdapter.Holder holder, int position) {


        Article article = list.get(position);
        holder.textViewAuthor.setText(article.getAuthor());
        holder.textViewTitle.setText(article.getTitle());
        //
        Picasso.get().load(article.getUrlToImage()).into(holder.imageViewNews);

        // Open Link When Clicked on CardView
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,WebViewActivity.class);
                intent.putExtra("URL_NAME",list.get(holder.getAdapterPosition()).getUrl());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textViewAuthor,textViewTitle;
        ImageView imageViewNews;
        public Holder(@NonNull  View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.tv_title);
            textViewAuthor =itemView.findViewById(R.id.tv_auther);
            imageViewNews=itemView.findViewById(R.id.image_news);


        }
    }
}
