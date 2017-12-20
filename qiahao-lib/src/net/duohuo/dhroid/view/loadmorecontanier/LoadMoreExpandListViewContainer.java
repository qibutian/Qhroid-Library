package net.duohuo.dhroid.view.loadmorecontanier;

import in.srain.cube.views.ptr.loadmore.LoadMoreContainerBase;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.ListView;

/**
 * @author huqiu.lhq
 */
public class LoadMoreExpandListViewContainer extends LoadMoreContainerBase {

	private ExpandableListView mListView;

	public LoadMoreExpandListViewContainer(Context context) {
		super(context);
	}

	public LoadMoreExpandListViewContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void addFooterView(View view) {
		mListView.addFooterView(view);
	}

	@Override
	protected void removeFooterView(View view) {
		mListView.removeFooterView(view);
	}

	@Override
	protected AbsListView retrieveAbsListView() {
		mListView = (ExpandableListView) getChildAt(0);
		return mListView;
	}
}
