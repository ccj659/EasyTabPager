package com.ccj.tabpager.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ccj.tabpager.base.Constant;
import com.ccj.tabpager.bean.DemoModel;
import com.ccj.tabpager.kaleadapter.CommonRcvAdapter;
import com.ccj.tabpager.kaleadapter.item.AdapterItem;
import com.ccj.tabpager.viewholder.ArticleViewHolder;
import com.ccj.tabpager.viewholder.ButtonViewHolder;
import com.ccj.tabpager.viewholder.TextViewHolder;

import java.util.List;

/**
 *  通过 getItemType(DemoModel demoModel）<br>
 * 进行type分类，然后判断通过 createItem(Object type) 新建对应类型的Holder<br>
 *  Created by chenchangjun on 17/3/23.<br>
 */

public class BaseCommonRcvAdapter extends CommonRcvAdapter<DemoModel> {

    private static final String TAG = "BaseCommonRcvAdapter";

    public BaseCommonRcvAdapter(@Nullable List data) {
        super(data);

    }

    @Override
    public Object getItemType(DemoModel demoModel) {
        if (demoModel.type == 1) {
            return Constant.VIEW_TYPE_ADVERT;
        }
        if (demoModel.type == 3) {
            return Constant.VIEW_TYPE_ADVERT_OTHER;
        }else {
            return Constant.VIEW_TYPE_NORMAL;
        }
    }

    @NonNull
    @Override
    public AdapterItem createItem(Object type) {
        Log.d(TAG, "createItem " + type + " view");
        switch (((int) type)) {
            case Constant.VIEW_TYPE_ADVERT:
                return new TextViewHolder();
            case Constant.VIEW_TYPE_NORMAL:
                return new ArticleViewHolder(BaseCommonRcvAdapter.this);
            case Constant.VIEW_TYPE_ADVERT_OTHER:
                return new ButtonViewHolder();
            default:
                throw new IllegalArgumentException("不合法的type");
        }
    }

}
