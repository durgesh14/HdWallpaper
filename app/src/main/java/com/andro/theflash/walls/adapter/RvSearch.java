package com.andro.theflash.walls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andro.theflash.walls.HdWallpaper_Activity;
import com.andro.theflash.walls.R;
import com.andro.theflash.walls.model.Walls;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class RvSearch extends RecyclerView.Adapter<RvSearch.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    private List<Walls> mData;

    public RvSearch(Context mContext, List lst) {


        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);
    }
    @NonNull
    @Override
    public RvSearch.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.search_row_items,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext,HdWallpaper_Activity.class);
                i.putExtra("wall",mData.get(viewHolder.getAdapterPosition()).getImg_url());
                i.putExtra("imagename",mData.get(viewHolder.getAdapterPosition()).getName());
                mContext.startActivity(i);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RvSearch.MyViewHolder holder, int position) {
        holder.wallname.setText(mData.get(position).getName());
        Glide.with(mContext).load(mData.get(position).getThumb_url()).apply(options).into(holder.wallThumb);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
/*-------------------------------------------------------------------------------------------------*/
    public void clear() {
        mData.clear();

        notifyDataSetChanged();
    }

    public void addall(List<Walls> wallpaper) {
        mData.addAll(wallpaper);
        notifyDataSetChanged();
    }
    /*-------------------------------------------------------------------------------------------------*/

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView wallname;
        ImageView wallThumb,hdWall;
        ConstraintLayout view_cont;


        public MyViewHolder(View itemView) {
            super(itemView);
            wallname = itemView.findViewById(R.id.thumb_namesearch);
            wallThumb = itemView.findViewById(R.id.thumbsearch);
            hdWall = itemView.findViewById(R.id.imageView);
            view_cont = itemView.findViewById(R.id.contsearch);

        }
    }

}
