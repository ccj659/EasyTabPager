package com.ccj.tabpager.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.ccj.tabpager.R;
import com.ccj.tabpager.ZDMListConfigBuilder;
import com.ccj.tabpager.adapter.BaseCommonRcvAdapter;
import com.ccj.tabpager.bean.DemoModel;
import com.ccj.tabpager.bean.Meizhi;
import com.ccj.tabpager.listener.OnSwipRefreshListener;
import com.ccj.tabpager.utils.DataManager;
import com.ccj.tabpager.view.ZDMSlidingTab;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by chenchangjun on 17/3/24.
 */

public class PagerRcvActivity extends AppCompatActivity implements OnSwipRefreshListener {

    private static final String TAG = PagerRcvActivity.class.getSimpleName();
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


        final List<DemoModel> data = DataManager.loadData(getBaseContext());
        // ((IAdapter<DemoModel>) getAdapter()).setData(data);

        //模拟加载tab标题
        String[] titles = {"标-题1", "标-题2", "标-题3", "标-题4", "标-题5", "标-题6", "标-题7"};

        setTabPagers(this, vpager, vtab, data, R.layout.vpager1, R.id.sr_layout, R.id.list, titles);

    }

    /**
     * @param <T>         泛型，强转为默认数据类型Meizhi.ResultsBean，可修改
     * @param mContext    上下文
     * @param vpager      ViewPager
     * @param vtab        ZDMSlidingTab
     * @param data        pager页面需要的数据
     * @param vpagerId    pager页面的资源id
     * @param sr_layoutId swiprefreshId
     * @param listId      recyvleVIewID
     * @param titles      数组形式的标题
     */
    public <T> void setTabPagers(Context mContext, ViewPager vpager, ZDMSlidingTab vtab, List<T> data, int vpagerId, int sr_layoutId, int listId, String[] titles) {
        builder = new ZDMListConfigBuilder(mContext);
        builder.setVpager(vpager).setVtab(vtab);
        for (int i = 0; i < titles.length; i++) {
            // 现在得到数据
            List<DemoModel> demoModels= DataManager.loadData(getBaseContext());
            // 添加到adapter
            BaseCommonRcvAdapter adapter = new BaseCommonRcvAdapter(demoModels);
//            builder.addTab(inflater.inflate(vpagerId, null), data, adapter, i, titles[i], sr_layoutId, listId, this);
        }
        builder.show();
    }





    @Override
    public <T> void onSwipRefresh(RecyclerView recyclerView, int pagerposition, String title, List<T> data) {

        Log.e(TAG, recyclerView.toString() + ",posion" + pagerposition + ",title " + data.toString());
        // 现在得到数据
        loadNewData(recyclerView, pagerposition, (List<DemoModel>) data);

    }

    @Override
    public <T> void onLoadMore(RecyclerView recyclerView, int position, String title, List<T> data) {

    }

    /**
     * 刷新RecycleView,中的数据
     *
     * @param data
     */
    private void loadNewData(final RecyclerView recyclerView, final int position, final List<DemoModel> data) {

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onRefresh" + data.toString());

                data.clear();
                data.addAll(DataManager.loadData(getBaseContext())); // 对data进行操作

                recyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新

                Toast.makeText(PagerRcvActivity.this, "refresh completed", Toast.LENGTH_SHORT).show();

                builder.getTabItem(position).getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 1000);
    }


    private <T> void OnLoaderMore(RecyclerView recyclerView, int pagerposition, String title, List<T> data){

        Log.e(TAG, recyclerView.toString() + ",posion" + pagerposition + ",title " + data.toString());
        // 现在得到数据
        loadMoreData(recyclerView, pagerposition, (List<DemoModel>) data);
    }

    private void loadMoreData(final RecyclerView recyclerView, final int pagerposition, final List<DemoModel> data) {
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onRefresh" + data.toString());

                data.addAll(DataManager.loadData(getBaseContext())); // 对data进行操作

                recyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新

                Toast.makeText(PagerRcvActivity.this, "refresh completed", Toast.LENGTH_SHORT).show();

                builder.getTabItem(pagerposition).getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 1000);

    }

}
