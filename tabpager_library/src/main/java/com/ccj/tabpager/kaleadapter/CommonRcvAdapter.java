package com.ccj.tabpager.kaleadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ccj.tabpager.kaleadapter.item.AdapterItem;
import com.ccj.tabpager.kaleadapter.util.IAdapter;
import com.ccj.tabpager.kaleadapter.util.ItemTypeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author ccj
 */
public abstract class CommonRcvAdapter<T> extends RecyclerView.Adapter<CommonRcvAdapter.RcvAdapterItem> implements IAdapter<T> {

    private List<T> mDataList;

    private Object mType;

    private ItemTypeUtil mUtil;

    private int currentPos;
    private int headerCount=0;//// TODO: 17/3/23 这个的作用什么？

    //item点击计数，显示错过的好价浮层时用到
    public int itemClickCount;


    public CommonRcvAdapter( List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }

       /* if (DataBindingJudgement.SUPPORT_DATABINDING && data instanceof ObservableList) {

            Log.e("DataBindingJudgement","DataBindingJudgement");
            ((ObservableList<T>) data)
                    .addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
                @Override
                public void onChanged(ObservableList<T> sender) {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                    notifyChange(sender, positionStart);
                }

                @Override
                public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                    notifyChange(sender, positionStart);
                }

                @Override
                public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
                    notifyChange(sender, Math.min(fromPosition, toPosition));
                }

                private void notifyChange(ObservableList<T> sender, int start) {
                    onItemRangeChanged(sender, start, getItemCount() - start);
                }

            });
        }*/
        mDataList = data;
        mUtil = new ItemTypeUtil();
    }


    public T getItem(int position) {
        Log.e("onItemClick",",mDataList-"+mDataList.toString());

        if (position < headerCount || position > getItemCount() - 1) {
            return null;
        }

        return mDataList.get(position - headerCount);
    }


    /**
     * 配合RecyclerView的pool来设置TypePool
     */
    public void setTypePool(HashMap<Object, Integer> typePool) {
        mUtil.setTypePool(typePool);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void setData( List<T> data) {
        mDataList = data;
    }

    @Override
    public List<T> getData() {
        return mDataList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * instead by{@link #getItemType(Object)}
     * <p>
     * 通过数据得到obj的类型的type
     * 然后，通过{@link ItemTypeUtil}来转换位int类型的type
     */
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        this.currentPos = position;
        mType = getItemType(mDataList.get(position));
        return mUtil.getIntType(mType);
    }

    @Override
    public Object getItemType(T t) {
        return -1; // default
    }

    @Override
    public RcvAdapterItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RcvAdapterItem(parent.getContext(), parent, createItem(mType));
    }

    @Override
    public void onBindViewHolder(RcvAdapterItem holder, int position) {
        debug(holder);
        holder.item.handleData(getConvertedData(mDataList.get(position), mType), position);
    }

    @Override
    public Object getConvertedData(T data, Object type) {
        return data;
    }

    @Override
    public int getCurrentPosition() {
        return currentPos;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 内部用到的viewHold
    ///////////////////////////////////////////////////////////////////////////

    static class RcvAdapterItem extends RecyclerView.ViewHolder {

        protected AdapterItem item;

        boolean isNew = true; // debug中才用到

        RcvAdapterItem(Context context, ViewGroup parent, AdapterItem item) {
            super(LayoutInflater.from(context).inflate(item.getLayoutResId(), parent, false));
            this.item = item;
            this.item.bindViews(itemView);
            this.item.setViews();
        }
        
    }

    ///////////////////////////////////////////////////////////////////////////
    // For debug
    ///////////////////////////////////////////////////////////////////////////

    private void debug(RcvAdapterItem holder) {
        boolean debug = true;
        if (debug) {
            holder.itemView.setBackgroundColor(holder.isNew ? 0xffff0000 : 0xff00ff00);
            holder.isNew = false;
        }
    }

}
