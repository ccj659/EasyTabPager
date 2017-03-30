package com.ccj.example.viewholder;


import android.view.View;
import android.widget.TextView;

import com.ccj.example.R;
import com.ccj.example.bean.DemoModel;
import com.ccj.tabpager.kaleadapter.item.AdapterItem;


public class TextViewHolder implements AdapterItem<DemoModel> {

    @Override
    public int getLayoutResId() {
        return R.layout.demo_item_text;
    }

    TextView textView;

    @Override
    public void bindViews(View root) {
        textView = (TextView) root.findViewById(R.id.textView);

    }

    @Override
    public void setViews() {
        //Log.d(TextItem.class.getSimpleName(), "setViews--------->");
    }

    @Override
    public void handleData(DemoModel model, int position) {
        textView.setText(model.content + " pos=" + position);
    }

}

