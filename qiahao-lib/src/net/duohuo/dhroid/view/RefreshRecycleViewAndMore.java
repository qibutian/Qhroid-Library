package net.duohuo.dhroid.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import net.duohuo.dhroid.R;
import net.duohuo.dhroid.adapter.INetAdapter;
import net.duohuo.dhroid.adapter.INetAdapter.LoadSuccessCallBack;
import net.duohuo.dhroid.adapter.recycleadapter.RecyclerBaseAdapter;
import net.duohuo.dhroid.net.model.DResponse;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreDefaultFooterView;
import in.srain.cube.views.ptr.loadmore.LoadMoreHandler;
import in.srain.cube.views.ptr.loadmore.LoadMoreRecycleViewContainer;
import in.srain.cube.views.ptr.loadmore.LoadMoreUIHandler;

public class RefreshRecycleViewAndMore extends RefreshAndMoreBase<RecyclerView> {

    LoadMoreRecycleViewContainer loadMoreRecyclerViewContainer;

    public RefreshRecycleViewAndMore(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContentView(R.layout.include_refresh_recycleview_base);
    }

    @Override
    public void initLoadMoreContainer() {
        loadMoreV = new LoadMoreDefaultFooterView(getContext());
        loadMoreV.setVisibility(GONE);
        loadMoreRecyclerViewContainer = (LoadMoreRecycleViewContainer) findViewById(R.id.load_more_list_view_container);
        loadMoreRecyclerViewContainer.setLoadMoreView(loadMoreV);
        loadMoreRecyclerViewContainer
                .setLoadMoreUIHandler((LoadMoreUIHandler) loadMoreV);
        loadMoreRecyclerViewContainer.setAutoLoadMore(true);

        loadMoreRecyclerViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                if (mAdapter != null) {
                    mAdapter.showNext();
                }
            }
        });
    }

    public void setListViewPadding(int left, int top, int right, int bottom) {
        getContentView().setPadding(left, top, right, bottom);
    }

    public void setLayoutManager(LayoutManager manager) {

        getContentView().setLayoutManager(manager);
        loadMoreRecyclerViewContainer.init();
    }

    @Override
    public RecyclerView getContentView() {
        return (RecyclerView) loadMoreRecyclerViewContainer.getChildAt(0);

    }

    @Override
    public void initView() {
        emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
    }

    @Override
    public void setAdapter(INetAdapter adapter) {
        // TODO Auto-generated method stub
        mAdapter = adapter;
        ((RecyclerBaseAdapter) mAdapter).addFootView(loadMoreV);
        mAdapter.setOnLoadSuccess(new LoadSuccessCallBack() {

            @Override
            public void callBack(DResponse response) {

                if (onLoadSuccess != null) {
                    onLoadSuccess.loadSuccess(response);
                }

                if (mAdapter.getPageNo() == 1) {
                    if (mEmptyV != null) {
                        emptyLayout
                                .setVisibility(mAdapter.getValues().size() == 0 ? View.VISIBLE
                                        : View.GONE);
                    }

                    if (onEmptyListenser != null) {
                        onEmptyListenser
                                .onempty(mAdapter.getValues().size() != 0 ? false
                                        : true);
                    }

                    loadMoreRecyclerViewContainer
                            .setShowLoadingForFirstPage(mAdapter.hasMore());
                    loadMoreRecyclerViewContainer.loadMoreFinish(
                            !mAdapter.hasMore(), mAdapter.hasMore());
                } else {
                    loadMoreRecyclerViewContainer.loadMoreFinish(mAdapter
                            .getValues().size() != 0 ? false : true, mAdapter
                            .hasMore());

                }
                //
                mPtrFrame.refreshComplete();
            }

        });

        getContentView().setAdapter((Adapter) mAdapter);

        mPtrFrame.postDelayed(new Runnable() {

            @Override
            public void run() {
                mPtrFrame.autoRefresh(true);
            }
        }, 300);
    }

}
