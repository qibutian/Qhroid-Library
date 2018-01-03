package net.duohuo.dhroid.view;

import android.content.Context;
import android.view.View;

import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by zcm on 2018/1/3.
 */

public interface IRefreshHeaderView {

    View getHeaderView(Context context);

    PtrUIHandler getHandler(Context context);
}
