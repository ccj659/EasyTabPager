#EasyTabPager 

一个通用中间组件，简单通用的适配ViewPager,以及pager中的RecycleView
简化复杂的操作，简单直接。


##To Use
###In Gradle


###In Maven


###In Your Application

![](http://img.blog.csdn.net/20170330150152528?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY2NqNjU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

```java


        /*****************初始化控件************/
        comm_test_view=(ZDMCommonPagerView)findViewById(R.id.comm_test_view);
        /*****************初始化数据和adapter************/

        List<DemoModel> demoModels1 = loadData(getBaseContext());
        List<DemoModel> demoModels2 = loadData(getBaseContext());
        List<DemoModel> demoModels3= loadData(getBaseContext());
        

        BaseCommonRcvAdapter adapter1 = new BaseCommonRcvAdapter(demoModels1);
        BaseCommonRcvAdapter adapter2 = new BaseCommonRcvAdapter(demoModels2);
        BaseCommonRcvAdapter adapter3 = new BaseCommonRcvAdapter(demoModels3);
        //spinner设置adapteer
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, obj);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        /************设置监听*****************/


        comm_test_view.setOnClickListener(this);
        comm_test_view.setSpinner(adapter,this);
        
        /************增加Tab Pager页面*****************/
        /**
         * vpagerId - 每一个pager的vpagerId
         data - 每一个pager对应的data
         recycleAdapter - 每一个pager对应的 adapter
         title - pager的title
         swipRes - swipLayout的Id
         revrRes - recycleView的Id
         onSwipRefreshListener - 刷新refresh和加载更多loadmore 回调借口
         */
        comm_test_view.addTab(R.layout.vpager1, demoModels1, adapter1, "标-题1", R.id.sr_layout, R.id.list, this);
        comm_test_view.addTab(R.layout.vpager1, demoModels2, adapter2, "标-题2", R.id.sr_layout, R.id.list, this);
        comm_test_view.addTab(R.layout.vpager1, demoModels3, adapter3, "标-题3", R.id.sr_layout, R.id.list, this);
        /************展示方法*****************/
        comm_test_view.show();

```


##Example
![](https://github.com/ccj659/EasyTabPager/blob/master/easyTabPager2.gif)

##思路和类结构
本通用中间组件，思想在于，用一个组件，简单通用的适配ViewPager,以及pager中的RecycleView，并且将其中的监听事件，抽离暴露出来方便调用。方便独立调用，以及扩展。




###1.关于CommonAdapter

一个**拆分简化RecycleView中ViewHolder的Adapter**,详情请点击传送门[通过封装BaseAdapter和RecyclerView.Adapter得到的通用的，简易的Adapter](https://github.com/tianzhijiexian/CommonAdapter)

###2.关于TabItem

TabItem是一个类,就是每一个viewpager的对象， 持有Tab标签的名字，每个tab的View以及其中的RecyclerView 和SwipeRefreshLayout，还有每一个RecyclerView的Apdater(结合CommonAdapter). 它算是一个对象持有者。



```java 
public class TabItem {

    View view;//tab 持有的view，可能是recycleview
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    String title; //tab 持有的tab的title
    RecyclerView.Adapter baseRecycleAdapter;//备用，每个view持有一个adapter

    List data;//每一个pager Item 持有一个data
    
    ```
###3.关于ListConfig

他是`TabItem `的持有者，相当于业务层，持有`TabItem`的List集合，并负责对TabItem的存取，以及监听设置。内部持有`InPagerAdapter` 保证viewpager的正常运转。它来作为中间层，和外部调用者进行交互。

###4.ZDMListConfigBuilder(保留，未使用)

一个Builder类，负责创建Pager。主要方法是
```java

 * <code>
 *      builder = new ZDMListConfigBuilder(this)<br>
        .setVpager(vpager)<br>
        .setVtab(vtab)<br>
        .setRecycleAdapter(new TestAdapter(meiZhi, this))<br>
        .addTab((RecyclerView) inflater.inflate(R.layout.vpager, null),"标-题1")<br>
        .addTab((RecyclerView) inflater.inflate(R.layout.vpager, null),"标-题2")<br>
        .addTab((RecyclerView) inflater.inflate(R.layout.vpager, null),"标-题3")<br>
        .addTab((RecyclerView) inflater.inflate(R.layout.vpager, null),"标-题4")<br>
        .addTab((RecyclerView) inflater.inflate(R.layout.vpager, null),"标-题5")<br>
        .show();<br>
 *<br>
 *     </>

    public ZDMListConfigBuilder addTab(View view,List data,RecyclerView.Adapter recycleAdapter, int positon, String title, int swipRes, int revrRes, OnSwipRefreshListener onSwipRefreshListener) {
        config.addTab(view,data,recycleAdapter, positon,title,swipRes,revrRes,onSwipRefreshListener);
        return this;
    }


```

###5.关于ZDMCommonPagerView

本来想用上面的Builder，后来想想，还是直接封装在View中比较实在.....

该View作为通用控件。。。。 就这样吧。。。 

详情参看代码。

 
