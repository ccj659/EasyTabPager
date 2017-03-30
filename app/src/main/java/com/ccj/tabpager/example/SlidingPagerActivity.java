package com.ccj.tabpager.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;

import com.smzdm.client.android.R;
import com.smzdm.client.android.adapter.TestAdapter;
import com.smzdm.client.android.bean.Meizhi;
import com.smzdm.client.base.weidget.zdmlistview.ZDMListConfigBuilder;
import com.smzdm.client.base.weidget.zdmslindingtab.ZDMSlidingTab;

import java.util.ArrayList;
import java.util.List;

/**
 * SlidingTabPager 使用例子
 */
public class SlidingPagerActivity extends Activity {

    ZDMListConfigBuilder builder;

    LayoutInflater inflater;
    private ArrayList<Meizhi.ResultsBean> meiZhi;
    ZDMSlidingTab vtab;
    ViewPager vpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        inflater = getLayoutInflater();
        vtab = (ZDMSlidingTab) findViewById(R.id.vtab);
        vpager = (ViewPager) findViewById(R.id.vpager);

        //模拟加载数据
        meiZhi = new ArrayList<>();
        meiZhi.add(new Meizhi.ResultsBean());
        meiZhi.add(new Meizhi.ResultsBean());
        meiZhi.add(new Meizhi.ResultsBean());
        Log.e("MeiZhiAdapter", "meiZhi.size()" + meiZhi.size());

        //模拟加载tab标题
        String[] titles = {"标-题1", "标-题2", "标-题3"};

        setTabPagers(this, vpager, vtab, meiZhi, R.layout.vpager, titles);
        //setTabPagers1(this, vpager, vtab, meiZhi, R.layout.vpager, titles);

    }


    /**
     *
     * @param mContext 上下文
     * @param vpager ViewPager
     * @param vtab ZDMSlidingTab
     * @param data  pager页面需要的数据
     * @param resouce pager页面的资源
     * @param titles 数组形式的标题
     * @param <T> 泛型，强转为默认数据类型Meizhi.ResultsBean，可修改
     */
    public <T>  void setTabPagers(Context mContext, ViewPager vpager, ZDMSlidingTab vtab, ArrayList<T> data, int resouce, String[] titles) {
        builder = new ZDMListConfigBuilder(this);
        builder.setVpager(vpager)
                .setVtab(vtab)
                .setRecycleAdapter(new TestAdapter((List<Meizhi.ResultsBean>) data, mContext));

        for (int i = 0; i < titles.length; i++) {
          //  builder.addTab((RecyclerView) inflater.inflate(resouce, null), titles[i]);
        }
        builder.show();
    }

/*
    public <T> void setTabPagers1(Context mainActivity, ViewPager vpager, ZDMSlidingTab vtab,  ArrayList<T> data, int resouce, String[] titles) {
        builder = new ZDMListConfigBuilder(this);
        builder.setVpager(vpager).setVtab(vtab).setRecycleAdapter(new TestAdapter((List<Meizhi.ResultsBean>) data, mainActivity));
        for (int i = 0; i < titles.length; i++) {
            builder.addTab((RecyclerView) inflater.inflate(resouce, null),
                    new TestAdapter((List<Meizhi.ResultsBean>) data, mainActivity),
                    titles[i]);
        }
        builder.show();
    }*/

}
