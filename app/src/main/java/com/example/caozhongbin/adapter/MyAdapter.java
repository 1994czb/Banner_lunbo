package com.example.caozhongbin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.caozhongbin.R;
import com.example.caozhongbin.bean.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {
    private Context context;
    private List<Bean.TopStoriesBean> list;

    public MyAdapter(Context context, List<Bean.TopStoriesBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewholder(inflate);
    }


    //点击事件接口
    public interface OnItemClickListener {
        void onItemClick(View view, String position);
    }


    private OnItemClickListener mItemClickListener;


    //设置单击事件接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(MyViewholder holder, final int position) {

        holder.tv_news.setText(list.get(position).getTitle());
        Glide.with(context).load(list.get(position).getImage()).into(holder.iv_news);
        holder.iv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view,list.get(position).getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder {

        private final ImageView iv_news;
        private final TextView tv_news;

        public MyViewholder(View itemView) {
            super(itemView);
            iv_news = itemView.findViewById(R.id.iv_news);
            tv_news = itemView.findViewById(R.id.tv_news);
        }
    }
}
