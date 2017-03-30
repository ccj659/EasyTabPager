package com.ccj.tabpager.listener;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by chenchangjun on 17/3/24.
 */

public interface OnSwipRefreshListener {

    /**
     * swiplayout Refresh的监听
     *
     * @param recyclerView 针对的哪个list
     * @param position     针对 pager上的那一页
     * @param title        页面的title
     * @param data         每一个pager要刷新的数据
     */
    <T> void onSwipRefresh(RecyclerView recyclerView, int position, String title, List<T> data);


    /**
     * SuperRecycleView LoadMore的监听
     *
     * @param recyclerView 针对的哪个list
     * @param position     针对 pager上的那一页
     * @param title        页面的title
     * @param data         每一个pager要刷新的数据
     */
    <T> void onLoadMore(RecyclerView recyclerView, int position, String title, List<T> data);

}
