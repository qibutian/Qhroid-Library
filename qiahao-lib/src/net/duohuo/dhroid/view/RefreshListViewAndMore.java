package net.duohuo.dhroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import net.duohuo.dhroid.R;
import net.duohuo.dhroid.adapter.INetAdapter;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainerBase;



public class RefreshListViewAndMore extends RefreshAndMoreBase<ListView> {

	public boolean autoRefresh = true;

	public RefreshListViewAndMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		setContentView(R.layout.include_refresh_listview_base);
	}

	public void setListViewPadding(int left, int top, int right, int bottom) {
		getContentView().setPadding(left, top, right, bottom);
	}

	public void addHeadView(View headV) {
		mheadV = headV;
		getContentView().addHeaderView(headV);
	}

	public boolean isAutoRefresh() {
		return autoRefresh;
	}

	public void setAutoRefresh(boolean autoRefresh) {
		this.autoRefresh = autoRefresh;
	}

	public void removeHeadView() {
		if (mheadV != null) {
			mheadV.setVisibility(View.GONE);
			mheadV.setPadding(0, -mheadV.getHeight(), 0, 0);
		}
	}

	public void showHeadView() {
		if (mheadV != null) {
			mheadV.setVisibility(View.VISIBLE);
			mheadV.setPadding(0, 0, 0, 0);
		}
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
				if (autoRefresh) {
					mPtrFrame.autoRefresh(true);
				}

				// if (mAdapter != null) {
				// mAdapter.refresh();
				// }
			}
		}, 300);
	}

}
