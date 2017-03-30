package com.ccj.tabpager.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ccj.tabpager.ListConfig;
import com.ccj.tabpager.R;
import com.ccj.tabpager.TabItem;
import com.ccj.tabpager.listener.OnSwipRefreshListener;

import java.util.List;

/**
 * 由焦点图+二级tab切换+简单筛选+列表元素构成，
 * 并增加分享功能（文案配置，链接自带邀请属性）<br>
 * 使用方法，请参考 testapplication中 example中的{@code  TestCommonPagerActivity}
 *
 * Created by chenchangjun on 17/3/28.
 */

public class ZDMCommonPagerView extends LinearLayout {

    private Context context;

    private ImageView comm_iv_show, comm_iv_transport;
    private Button comm_btn_focus;
    private ZDMSlidingTab comm_vtab;
    private ViewPager comm_vpager;
    private Spinner comm_spinner;
    private ListConfig config;


    private int positon = 0;



    private void init(Context context, AttributeSet attrs) {
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.zdm_common_list_pager, this, true);//通用布局
        this.context = context;
        if (attrs == null) {
            return;
        }
        config = new ListConfig(context);
      /*  //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.zdm_common_list_pager);
        titleText = a.getString(R.styleable.zdm_common_list_pager_titleText);
        titleTextColor = a.getColor(R.styleable.zdm_common_list_pager_titleTextColor, Color.WHITE);
        titleTextSize = a.getDimension(R.styleable.zdm_common_list_pager_titleTextSize, 20f);
        //回收资源，这一句必须调用
        a.recycle();
*/
    }


    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //获取子控件

        comm_iv_show = (ImageView) findViewById(R.id.comm_iv_show);
        comm_iv_transport = (ImageView) findViewById(R.id.comm_iv_show);
        comm_btn_focus = (Button) findViewById(R.id.comm_btn_focus);
        comm_vtab = (ZDMSlidingTab) findViewById(R.id.comm_vtab);
        comm_vpager = (ViewPager) findViewById(R.id.comm_vpager);
        comm_spinner = (Spinner) findViewById(R.id.comm_spinner);

    /*    //将从资源文件中加载的属性设置给子控件
        if (!TextUtils.isEmpty(titleText))
            setPageTitleText(titleText);
        setPageTitleTextColor(titleTextColor);
        setPageTitleTextSize(titleTextSize);*/


    }
    public TabItem getTabItem(int position){

        return config.getTabs().get(position);

    }

    public void setShowBitmap(Bitmap showBitmap){
        comm_iv_show.setImageBitmap(showBitmap);

    }
    /**
     *
     * @param l
     *
     *
     */
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        comm_iv_show.setOnClickListener(l);
        comm_iv_transport.setOnClickListener(l);
        comm_btn_focus.setOnClickListener(l);
        comm_vtab.setOnClickListener(l);
        comm_vpager.setOnClickListener(l);
      //  comm_spinner.setOnClickListener(l);

    }

    /**
     * @param vpagerId 每一个pager的vpagerId
     * @param data 每一个pager对应的data
     * @param recycleAdapter 每一个pager对应的 adapter
     * @param title pager的title
     * @param swipRes swipLayout的Id
     * @param revrRes recycleView的Id
     * @param onSwipRefreshListener 刷新refresh和加载更多loadmore 回调借口<br>
     * @return
     */
    public void addTab(int vpagerId, List data, RecyclerView.Adapter recycleAdapter, String title, int swipRes, int revrRes, OnSwipRefreshListener onSwipRefreshListener) {
        config.addTab(inflate(context, vpagerId, null), data, recycleAdapter, positon, title, swipRes, revrRes, onSwipRefreshListener);
        ++positon;
    }

    /**
     * 设置 筛选条件 spinner 以及监听
     * @param adapter spinner的adapter
     * @param listener
     */
    public void setSpinner(SpinnerAdapter adapter, AdapterView.OnItemSelectedListener listener) {
        comm_spinner.setAdapter(adapter);
        comm_spinner.setOnItemSelectedListener(listener);
    }

    /**
     * 展示
     *
     * @return
     */
    public void show() {
        comm_vpager.setAdapter(config.createInPagerAdapter());
        comm_vtab.setViewPager(comm_vpager);
    }


    public ZDMCommonPagerView(Context context) {
        super(context);
        init(context, null);

    }

    public ZDMCommonPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ZDMCommonPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ZDMCommonPagerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);

    }



    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getComm_iv_show() {
        return comm_iv_show;
    }

    public void setComm_iv_show(ImageView comm_iv_show) {
        this.comm_iv_show = comm_iv_show;
    }

    public ImageView getComm_iv_transport() {
        return comm_iv_transport;
    }

    public void setComm_iv_transport(ImageView comm_iv_transport) {
        this.comm_iv_transport = comm_iv_transport;
    }

    public Button getComm_btn_focus() {
        return comm_btn_focus;
    }

    public void setComm_btn_focus(Button comm_btn_focus) {
        this.comm_btn_focus = comm_btn_focus;
    }

    public ZDMSlidingTab getComm_vtab() {
        return comm_vtab;
    }

    public void setComm_vtab(ZDMSlidingTab comm_vtab) {
        this.comm_vtab = comm_vtab;
    }

    public ViewPager getComm_vpager() {
        return comm_vpager;
    }

    public void setComm_vpager(ViewPager comm_vpager) {
        this.comm_vpager = comm_vpager;
    }

    public Spinner getComm_spinner() {
        return comm_spinner;
    }

    public void setComm_spinner(Spinner comm_spinner) {
        this.comm_spinner = comm_spinner;
    }

    public ListConfig getConfig() {
        return config;
    }

    public void setConfig(ListConfig config) {
        this.config = config;
    }

    public int getPositon() {
        return positon;
    }

    public void setPositon(int positon) {
        this.positon = positon;
    }



}
