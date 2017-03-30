package com.ccj.tabpager.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Toast;

import com.ccj.tabpager.R;
import com.ccj.tabpager.listener.OnAppBarSkipListener;
import com.ccj.tabpager.listener.OnLoadNextListener;

public class SuperRecyclerView extends RecyclerView {

    private OnLoadNextListener mLoadNextListener;
    private OnAppBarSkipListener mAppBarSkipListener;

    //是否正在加载
    private boolean isLoading = false;
    //是否加载到最后
    private boolean isEnd = false;
    //没有更多提示是否已显示过
    private boolean toastHasShown = false;

    private int mActionBarAutoHideSensivity = 0;
    private int mActionBarAutoHideMinY = 0;
    private int mActionBarAutoHideSignal = 0;

    private Context context;

    public SuperRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public SuperRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SuperRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void setOnAppBarSkipListener(OnAppBarSkipListener listener) {
        mAppBarSkipListener = listener;
    }

    private void init(final Context context) {

        this.context=context;

        addOnScrollListener(new OnScrollListener() {
            final static int ITEMS_THRESHOLD = 1;
            int lastFvi = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager mLayoutManager = (LinearLayoutManager) getLayoutManager();
                int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                if (lastFvi != firstVisibleItem) {
                    if (lastFvi > 0) {
                        onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0 : Integer.MAX_VALUE,
                                lastFvi - firstVisibleItem > 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE
                        );
                    }
                    lastFvi = firstVisibleItem;
                }
                int totalItemCount = mLayoutManager.getItemCount();
                if (isEnd) {
                    if (!toastHasShown) {
                        //加载到最后，判断是否拉到底部
                        if (!canScrollVertically(1)) {
                            if (canScrollVertically(-1)) {//只有内容超过一屏时才触发提示
                                Toast.makeText(context, getResources().getString(R.string.no_more), Toast.LENGTH_SHORT).show();
                                toastHasShown = true;
                            }
                        }
                    }
                } else if (!isLoading && mLoadNextListener != null) {
                    int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                    //lastVisibleItem >= totalItemCount - 3 表示剩下2个item自动加载(因为包含Banner)
                    // dy>0 表示向下滑动
                    if (totalItemCount > 5 && lastVisibleItem >= totalItemCount - 3 && dy > 0) {
                        isLoading = true;
                        mLoadNextListener.onLoadNext();
                    }
                }
                //用于防止首页好价的toolbar 因快速的显示隐藏而抖动
                if (null != mAppBarSkipListener) {
                    if (firstVisibleItem > ITEMS_THRESHOLD) {
                        if (Math.abs(dy) < 15) {
                            mAppBarSkipListener.isSkip(true);
                        } else {
                            mAppBarSkipListener.isSkip(false);
                        }
                    } else {
                        mAppBarSkipListener.isSkip(false);
                    }
                }
            }
        });
        initActionBarAutoHide();
    }

    public void setLoadNextListener(OnLoadNextListener listener) {
        mLoadNextListener = listener;
    }

    public void setLoadingState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean getLoadingState() {
        return isLoading;
    }

    /**
     * 设置是否加载到最后
     *
     * @param isEnd true:不再触发翻页 false:正常翻页
     */
    public void setLoadToEnd(boolean isEnd) {
        if (!isEnd) {
            //列表已刷新，重置toastHasShown状态
            toastHasShown = false;
        }
        this.isEnd = isEnd;
    }

    /**
     * Initializes the Action Bar auto-hide (aka Quick Recall) effect.
     */
    protected void initActionBarAutoHide() {
        mActionBarAutoHideMinY = getResources().getDimensionPixelSize(
                R.dimen.action_bar_auto_hide_min_y);
        mActionBarAutoHideSensivity = getResources().getDimensionPixelSize(
                R.dimen.action_bar_auto_hide_sensivity);
    }

    /**
     * Copied from google io 2014 by Aidi
     * Indicates that the main content has scrolled (for the purposes of showing/hiding
     * the action bar for the "action bar auto hide" effect). currentY and deltaY may be exact
     * (if the underlying view supports it) or may be approximate indications:
     * deltaY may be INT_MAX to mean "scrolled forward indeterminately" and INT_MIN to mean
     * "scrolled backward indeterminately".  currentY may be 0 to mean "somewhere close to the
     * start of the list" and INT_MAX to mean "we don't know, but not at the start of the list"
     */
    public void onMainContentScrolled(int currentY, int deltaY) {
        if (mLoadNextListener != null) {
            if (deltaY > mActionBarAutoHideSensivity) {
                deltaY = mActionBarAutoHideSensivity;
            } else if (deltaY < -mActionBarAutoHideSensivity) {
                deltaY = -mActionBarAutoHideSensivity;
            }

            if (Math.signum(deltaY) * Math.signum(mActionBarAutoHideSignal) < 0) {
                // deltaY is a motion opposite to the accumulated signal, so reset signal
                mActionBarAutoHideSignal = deltaY;
            } else {
                // add to accumulated signal
                mActionBarAutoHideSignal += deltaY;
            }
            boolean shouldShow = currentY < mActionBarAutoHideMinY ||
                    (mActionBarAutoHideSignal <= -mActionBarAutoHideSensivity);
            mLoadNextListener.autoShowOrHideToolbar(shouldShow);
        }
    }

}
