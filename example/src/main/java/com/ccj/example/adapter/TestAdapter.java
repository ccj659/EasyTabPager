package com.ccj.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccj.example.R;
import com.ccj.example.bean.Meizhi;
import com.ccj.tabpager.listener.ItemClickListener;
import com.ccj.tabpager.listener.ItemLongClickListener;

import java.util.List;

/**
 * Created by chenchangjun on 17/3/21.
 */

public class TestAdapter  extends RecyclerView.Adapter<TestAdapter.MyViewHolder>  {


    protected List<Meizhi.ResultsBean> tList;
    protected Context context;

    public ItemClickListener onItemClickListener;
    public ItemLongClickListener onItemLongClickListener;

    public TestAdapter(List<Meizhi.ResultsBean> tList, Context context) {

        this.tList = tList;
        this.context = context;
    }
    public void setOnItemClickListener(ItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    public void setData(List<Meizhi.ResultsBean> list){
        this.tList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(
                R.layout.item_meizhi_adapter,parent,false));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(tList.get(position).toString());
    }



    @Override
    public int getItemCount() {
        return tList==null?0:tList.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.image);
            textView= (TextView) itemView.findViewById(R.id.tv_test);
        }

    }
}
