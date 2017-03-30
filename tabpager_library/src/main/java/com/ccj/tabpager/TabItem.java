package com.ccj.tabpager;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by ccj on 2017/3/20.
 */

public class TabItem {


    View view;//tab 持有的view，可能是recycleview
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    String title; //tab 持有的tab的title
    RecyclerView.Adapter baseRecycleAdapter;//备用，每个view持有一个adapter

    List data;//每一个pager Item 持有一个data

    public View getView() {
        return view;
    }
    
    public void setView(View view) {
        this.view = view;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public TabItem(View view, RecyclerView.Adapter baseRecycleAdapter, String title) {
        this.view = view;
        this.title = title;
        this.baseRecycleAdapter=baseRecycleAdapter;

    }


    
    public TabItem(View view, String title, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        this.view = view;
        this.title = title;
        this.recyclerView= recyclerView;
        this.swipeRefreshLayout= swipeRefreshLayout;
    }


    public TabItem(View view, List data, RecyclerView.Adapter baseRecycleAdapter, String title, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        this.view = view;
        this.data=data;
        this.title = title;
        this.recyclerView= recyclerView;
        this.swipeRefreshLayout= swipeRefreshLayout;
        this.baseRecycleAdapter=baseRecycleAdapter;
    }



    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public RecyclerView.Adapter getBaseRecycleAdapter() {
        return baseRecycleAdapter;
    }

    public void setBaseRecycleAdapter(RecyclerView.Adapter baseRecycleAdapter) {
        this.baseRecycleAdapter = baseRecycleAdapter;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
