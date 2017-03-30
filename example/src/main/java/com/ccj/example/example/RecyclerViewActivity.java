package com.ccj.example.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ccj.example.R;
import com.ccj.example.adapter.BaseCommonRcvAdapter;
import com.ccj.example.bean.DemoModel;
import com.ccj.example.utils.DataManager;
import com.ccj.tabpager.kaleadapter.CommonRcvAdapter;
import com.ccj.tabpager.kaleadapter.util.IAdapter;

import java.util.List;


/**
 * @author ccj
 * @date 2017/3/23
 * @参照 recycleView  中间层 使用例子
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewActivity";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //初始化recycleView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(layoutManager);
        // 放一个默认空数据
        mRecyclerView.setAdapter(getAdapter(null));

        // 现在得到数据
        final List<DemoModel> data = DataManager.loadData(getBaseContext());
        ((IAdapter<DemoModel>) mRecyclerView.getAdapter()).setData(data); // 设置新的数据
        mRecyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新


        //刷新数据
        loadNewData(data);
    }

    /**
     * 刷新RecycleView,中的数据
     * @param data
     */
    private void loadNewData(final List<DemoModel> data) {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                data.clear();
                data.addAll(DataManager.loadData(getBaseContext())); // 对data进行操作
                mRecyclerView.getAdapter().notifyDataSetChanged(); // 通知数据刷新
                Toast.makeText(RecyclerViewActivity.this, "refresh completed", Toast.LENGTH_SHORT).show();
            }
        }, 1000);
    }

    /**
     * CommonAdapter的类型和item的类型是一致的
     * 这里的都是{@link DemoModel}
     * 多种类型的type
     */
    private CommonRcvAdapter<DemoModel> getAdapter(List<DemoModel> data) {return new BaseCommonRcvAdapter(data);}


}
