package com.simmi.foundation.admin;
import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.simmi.foundation.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {


    ArrayList<mainmodel> list;
    private  RecyclerViewClickListner listner;

    public MainAdapter( ArrayList<mainmodel> list,RecyclerViewClickListner listner) {

        this.list = list;
        this.listner= listner;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        mainmodel mainmodel=list.get(position);
        holder.date.setText(mainmodel.getDate());
        holder.descp.setText(mainmodel.getDescription());
        holder.title.setText(mainmodel.getTitle());





        Glide.with(holder.imageView.getContext())
                .load(mainmodel.getSurl())
                .placeholder(R.drawable.loading)
                .into(holder.imageView);
        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String delete= list.get(position).getTitle();

                makeText(view.getContext(), "Blog Deleted Successfully", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Blog").child(delete);
            ref.removeValue();
            }
        });
        holder.clcik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(holder.title.getContext(),blogmainscreen.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("imgurl",list.get(position).getSurl());
                intent.putExtra("descp",list.get(position).getDescription());
                intent.putExtra("DATE",list.get(position).getDate());

                holder.title.getContext().startActivity(intent);

            }
        });
        holder.shareblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody ="Hey I just watch this amazing blog at SIMMI Foundation App.\nI think you should also check this out.  \"\""+list.get(position).getShare();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SIMMI FOUNDATION");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                holder.shareblog.getContext().startActivity(sharingIntent);

            }

        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface   RecyclerViewClickListner{
        void OnClick(View v, int postion);
    }
    public  class MyViewHolder extends  RecyclerView.ViewHolder  {
        // readmore;
        ImageView imageView;
        TextView title,descp,date,shareblog;
        CardView clcik;
        ImageButton delete;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            title=itemView.findViewById(R.id.title);
            descp=itemView.findViewById(R.id.descp);
            date=itemView.findViewById(R.id.date);
            clcik=itemView.findViewById(R.id.main_layout);
            delete=itemView.findViewById(R.id.delete);
            ViewPager mViewPager = itemView.findViewById(R.id.viewPage);
            shareblog=itemView.findViewById(R.id.shareblog);

        }



    }
}