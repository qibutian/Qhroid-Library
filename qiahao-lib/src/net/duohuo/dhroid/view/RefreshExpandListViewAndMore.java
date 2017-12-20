package net.duohuo.dhroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;


import net.duohuo.dhroid.R;
import net.duohuo.dhroid.adapter.INetAdapter;
import net.duohuo.dhroid.adapter.INetAdapter.LoadSuccessCallBack;
import net.duohuo.dhroid.net.model.DResponse;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.loadmore.LoadMoreContainerBase;

public class RefreshExpandListViewAndMore extends
		RefreshAndMoreBase<ExpandableListView> {
	BaseExpandableListAdapter exadapter;

	public RefreshExpandListViewAndMore(Context context, AttributeSet attrs) {
		super(context, attrs);
		setContentView(R.layout.include_refresh_expandlistview_base);
	}

	@Override
	public void initView() {
		emptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
		mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptr_frame);
		loadMoreContainer = (LoadMoreContainerBase) findViewById(R.id.load_more_list_view_container);
	}

	public void setRealAdapter(BaseExpandableListAdapter exadapter) {
		this.exadapter = exadapter;
	}

	@Override
	public void setAdapter(INetAdapter adapter) {
		mAdapter = adapter;

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

					loadMoreContainer.setShowLoadingForFirstPage(mAdapter
							.hasMore());
					loadMoreContainer.loadMoreFinish(!mAdapter.hasMore(),
							mAdapter.hasMore());
				} else {
					loadMoreContainer.loadMoreFinish(mAdapter.getValues()
							.size() != 0 ? false : true, mAdapter.hasMore());

				}

				mPtrFrame.refreshComplete();
			}

		});

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
