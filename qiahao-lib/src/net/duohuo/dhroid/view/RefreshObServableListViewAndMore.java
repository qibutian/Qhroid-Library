package net.duohuo.dhroid.view;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainerBase;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListAdapter;


import net.duohuo.dhroid.R;
import net.duohuo.dhroid.adapter.INetAdapter;
import net.duohuo.dhroid.scroller.ObservableListView;

public class RefreshObServableListViewAndMore extends
		RefreshAndMoreBase<ObservableListView> {

	public RefreshObServableListViewAndMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		setContentView(R.layout.include_refresh_observable_base);
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
