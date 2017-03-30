package com.ccj.tabpager.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.smzdm.client.android.R;
import com.smzdm.client.android.adapter.BaseCommonRcvAdapter;
import com.smzdm.client.android.bean.DemoModel;
import com.smzdm.client.android.utils.DataManager;
import com.smzdm.client.base.weidget.zdmlistview.ZDMCommonPagerView;
import com.smzdm.client.base.weidget.zdmlistview.listener.OnSwipRefreshListener;

import java.util.Collection;
import java.util.List;

import static com.smzdm.client.android.utils.DataManager.loadData;

/**
 * Created by chenchangjun on 17/3/28.
 */

public class TestCommonPagerActivity extends AppCompatActivity implements View.OnClickListener,OnSwipRefreshListener ,AdapterView.OnItemSelectedListener{

    private static final String TAG = TestCommonPagerActivity.class.getSimpleName();

    private String obj[]={"abc","cbd","acd","efg"};

    private ZDMCommonPagerView comm_test_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_pager);

        comm_test_view=(ZDMCommonPagerView)findViewById(R.id.comm_test_view);


        List<DemoModel> demoModels1 = DataManager.loadData(getBaseContext());
        List<DemoModel> demoModels2 = DataManager.loadData(getBaseContext());
        List<DemoModel> demoModels3= DataManager.loadData(getBaseContext());

        BaseCommonRcvAdapter adapter1 = new BaseCommonRcvAdapter(demoModels1);
        BaseCommonRcvAdapter adapter2 = new BaseCommonRcvAdapter(demoModels2);
        BaseCommonRcvAdapter adapter3 = new BaseCommonRcvAdapter(demoModels3);


        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, obj);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        comm_test_view.setOnClickListener(this);
        comm_test_view.setSpinner(adapter,this);

        comm_test_view.addTab(R.layout.vpager1, demoModels1, adapter1, "标-题1", R.id.sr_layout, R.id.list, this);
        comm_test_view.addTab(R.layout.vpager1, demoModels2, adapter2, "标-题2", R.id.sr_layout, R.id.list, this);
        comm_test_view.addTab(R.layout.vpager1, demoModels3, adapter3, "标-题3", R.id.sr_layout, R.id.list, this);
        comm_test_view.show();

    }




    /**
     * 刷新RecycleView,中的数据
     *
     * @param data
     */
    @Override
    public <T> void onSwipRefresh(final RecyclerView recyclerView, final int pagerposition, String title, final List<T> data) {

        Log.e(TAG, recyclerView.toString() + ",posion" + pagerposition + ",title " + data.toString());
        // 现在得到数据
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onRefresh" + data.toString());

                data.clear();
                data.addAll((Collection<? extends T>) loadData(getBaseContext())); // 对data进行操作

                recyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新

                Toast.makeText(TestCommonPagerActivity.this, "refresh completed", Toast.LENGTH_SHORT).show();

                comm_test_view.getTabItem(pagerposition).getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 1000);

    }
    /**
     * 刷新RecycleView,中的数据
     *
     * @param data
     */
    @Override
    public <T> void onLoadMore(final RecyclerView recyclerView, final int position, String title, final List<T> data) {
        Log.e(TAG, recyclerView.toString() + ",posion" + position + ",title " + data.toString());
        comm_test_view.getTabItem(position).getSwipeRefreshLayout().setRefreshing(true);
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onLoadMore" + data.toString());

                data.addAll((Collection<? extends T>) loadData(getBaseContext())); // 对data进行操作

                recyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新

                Toast.makeText(TestCommonPagerActivity.this, "refresh completed", Toast.LENGTH_SHORT).show();

                comm_test_view.getTabItem(position).getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 1000);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemSelected" + position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }
}
