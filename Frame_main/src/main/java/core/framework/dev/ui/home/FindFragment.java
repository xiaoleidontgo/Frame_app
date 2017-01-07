package core.framework.dev.ui.home;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import core.framework.dev.R;
import core.framework.dev.adapter.MessageAdapter;
import lyh.library.cm.basic.fragment.LazyFragment;
import lyh.library.cm.basic.fragment.SwipeRefreshListFragment;
import lyh.library.cm.widget.UiToolbar;

/**
 * Created by lyh on 2016/10/25.
 * 这个界面不启用。可作为RotateTab标签的替换
 */

public class FindFragment extends LazyFragment {

    public static FindFragment newInstance() {
        FindFragment findFragment = new FindFragment();
        Bundle bundle = new Bundle();
        findFragment.setArguments(bundle);
        return findFragment;
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_me;
    }
}
