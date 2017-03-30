package com.ccj.example.viewholder;

import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccj.example.R;
import com.ccj.example.bean.DemoModel;
import com.ccj.tabpager.kaleadapter.CommonRcvAdapter;
import com.ccj.tabpager.kaleadapter.item.AdapterItem;
import com.ccj.tabpager.listener.OnHolderClickListener;


/**
 * Created by chenchangjun on 17/3/23.
 */

public class ArticleViewHolder implements AdapterItem<DemoModel> {

    private static final String TAG = "ArticleViewHolder";

    private int mPosition;
    private DemoModel data=new DemoModel();

    private ImageView iv_pic;
    private TextView tv_title, tv_mall, tv_time, tv_price, tv_comment, tv_tag, tv_zhi;
    private RelativeLayout rl_mall_time;
    private View itemView;
    private CommonRcvAdapter rcvAdapter;
    private OnHolderClickListenerImp mListener;


    public ArticleViewHolder(CommonRcvAdapter rcvAdapter) {

        this.rcvAdapter=rcvAdapter;
    }

    @Override
    public int getLayoutResId() {
        return  R.layout.item_jingxuan;
    }

    @Override
    public void bindViews(View itemView) {

        this.itemView=itemView;
        iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_mall = (TextView) itemView.findViewById(R.id.tv_mall);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        tv_comment = (TextView) itemView.findViewById(R.id.tv_comment);
        tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
        tv_zhi = (TextView) itemView.findViewById(R.id.tv_zhi);

        rl_mall_time = (RelativeLayout) itemView.findViewById(R.id.rl_mall_time);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            rl_mall_time.setGravity(Gravity.LEFT);
        } else {
            rl_mall_time.setGravity(Gravity.START);
        }
    }

    @Override
    public void setViews() {
        mListener=new OnHolderClickListenerImp();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //// TODO: 17/3/23 事件监听
                mListener.onItemClick(mPosition, data.type);

            }
        });
    }

    @Override
    public void handleData(DemoModel demoModel, int position) {
        mPosition = position;
        this.data=demoModel;
        Log.e("handleData","position-"+ mPosition +demoModel.toString());


        tv_comment.setText(demoModel.toString() + "");
    }


   class OnHolderClickListenerImp implements OnHolderClickListener {
       @Override
       public void onItemClick(int position, int viewType) {
           Log.e("onItemClick","position-"+ position+",viewType-"+viewType);
           rcvAdapter.itemClickCount++;
       }
   }



}
