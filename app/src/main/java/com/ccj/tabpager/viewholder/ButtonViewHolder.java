package com.ccj.tabpager.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ccj.tabpager.R;
import com.ccj.tabpager.bean.DemoModel;
import com.ccj.tabpager.kaleadapter.item.AdapterItem;


/**
 * @tips 优化小技巧：这个就等于一个viewHolder，用于复用，所以不会重复建立对象
 */
public class ButtonViewHolder implements AdapterItem<DemoModel> {

    private static final String TAG = "ButtonItem";

    private int mPosition;
    
    private Button btn;

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_button;
    }

    @Override
    public void bindViews(final View root) {
        btn = (Button) root;
    }

    /**
     * @tips: 优化小技巧：在这里直接设置按钮的监听器。
     * 因为这个方法仅仅在item建立时才调用，所以不会重复建立监听器。
     */
    @Override
    public void setViews() {
        // 这个方法仅仅在item构建时才会触发，所以在这里也仅仅建立一次监听器，不会重复建立
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(btn.getContext(), "pos = " + mPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void handleData(DemoModel model, int position) {
        Log.d(TAG, "handleData: " + model.content);
        // 在每次适配器getView的时候就会触发，这里避免做耗时的操作
        mPosition = position;

        btn.setText(model.content);
    }

}
