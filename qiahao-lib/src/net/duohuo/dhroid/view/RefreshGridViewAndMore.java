package net.duohuo.dhroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;


import net.duohuo.dhroid.R;
import net.duohuo.dhroid.adapter.INetAdapter;

import in.srain.cube.views.ptr.GridViewWithHeaderAndFooter;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainerBase;

public class RefreshGridViewAndMore extends
		RefreshAndMoreBase<GridViewWithHeaderAndFooter> {

	public RefreshGridViewAndMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		setContentView(R.layout.include_refresh_gridview_base);
	}

	public void setGridViewPadding(int left, int top, int right, int bottom) {
		getContentView().setPadding(left, top, right, bottom);
	}

	public void addHeadView(View headV) {
		mheadV = headV;
		getContentView().addHeaderView(headV);
	}

	public void addFootView(View footView) {
		getContentView().addFooterView(footView);
	}

	@Override
	public void initView() {
		emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
		mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
		loadMoreContainer = (LoadMoreContainerBase) findViewById(R.id.load_more_list_view_container);
	}

	@Override
	public void setAdapter(INetAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		getContentView().setAdapter((ListAdapter) mAdapter);

		mPtrFrame.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPtrFrame.autoRefresh(true);
				if (mAdapter != null) {
					mAdapter.refresh();
				}
			}
		}, 300);
	}

}
