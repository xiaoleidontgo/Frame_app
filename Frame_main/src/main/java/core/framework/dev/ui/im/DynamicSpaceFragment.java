package core.framework.dev.ui.im;

import android.os.Bundle;

import core.framework.dev.R;
import lyh.library.cm.basic.fragment.LceFragment;

/**
 * Created by lyh on 2016/10/31.
 * IM 下 控件
 */

public class DynamicSpaceFragment extends LceFragment {
    public static DynamicSpaceFragment newInstance() {
        Bundle args = new Bundle();
        DynamicSpaceFragment fragment = new DynamicSpaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeLazyRequest() {
        showEmptyView();
    }

    @Override
    public void lazyRequest() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_dynamic_space;
    }
}
