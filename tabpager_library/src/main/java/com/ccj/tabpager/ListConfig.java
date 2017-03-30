package com.ccj.tabpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ccj.tabpager.kaleadapter.util.IAdapter;
import com.ccj.tabpager.listener.OnLoadNextListener;
import com.ccj.tabpager.listener.OnSwipRefreshListener;
import com.ccj.tabpager.view.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccj on 2017/3/20.
 * 持有TabItem{@see TabItem} 的集合，并有添加等方法
 */

public class ListConfig {

    private static final String TAG =ListConfig.class.getSimpleName() ;
    private List<TabItem> tabs;
    private RecyclerView.Adapter mRecycleAdapter;
    private Context context;

    public ListConfig(Context context) {
        tabs = new ArrayList<>();
        this.context = context;
    }




    /**
       适用于所有view 对应 一个adapter <br>
     * 需要注意 "请先调用buidler.setRecycleAdapter() 方法"<br>
     *
     * @param view 每一个pager的view
     * @param data 每一个pager对应的data
     * @param recycleAdapter 每一个pager对应的 adapter
     * @param positon pager的位置
     * @param title pager的title
     * @param swipRes swipLayout的Id
     * @param revrRes recycleView的Id
     * @param onSwipRefreshListener 刷新回调借口<br>
     *  //todo 加载更多回调接口
     */
    public void addTab(View view, final List data, RecyclerView.Adapter recycleAdapter, final int positon, final String title, int swipRes, int revrRes, final OnSwipRefreshListener onSwipRefreshListener) {

        final SuperRecyclerView recyclerView = (SuperRecyclerView) view.findViewById(revrRes);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(swipRes);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(recycleAdapter);
        ((IAdapter) recyclerView.getAdapter()).setData(data); // 设置新的数据
        tabs.add(new TabItem(view, data,recycleAdapter,title, recyclerView, swipeRefreshLayout));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipRefreshListener.onSwipRefresh(recyclerView,positon,title,data);
            }
        });
        recyclerView.setLoadNextListener(new OnLoadNextListener() {
            @Override
            public void onLoadNext() {
                onSwipRefreshListener.onLoadMore(recyclerView,positon,title,data);
            }

            @Override
            public void autoShowOrHideToolbar(boolean show) {

            }
        });
    }


    /**
     * @return
     */
    public InPagerAdapter createInPagerAdapter() {
        return new InPagerAdapter();
    }

    /**
     * 设置全局适配器
     *
     * @param recycleAdapter
     */

    public void addRecycleAdapter(RecyclerView.Adapter recycleAdapter) {

        this.mRecycleAdapter = recycleAdapter;

    }


    public List<TabItem> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabItem> tabs) {
        this.tabs = tabs;
    }

    public RecyclerView.Adapter getmRecycleAdapter() {
        return mRecycleAdapter;
    }

    public void setmRecycleAdapter(RecyclerView.Adapter mRecycleAdapter) {
        this.mRecycleAdapter = mRecycleAdapter;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }



    private class InPagerAdapter extends PagerAdapter {

        public InPagerAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(tabs.get(position).view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            tabs.get(position).recyclerView.setLayoutManager(new LinearLayoutManager(context));
            tabs.get(position).recyclerView.setAdapter(tabs.get(position).baseRecycleAdapter);
            //tabs.get(position).recyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新
            container.addView(tabs.get(position).view);
            return tabs.get(position).view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs.get(position).title;
        }
    }


}
