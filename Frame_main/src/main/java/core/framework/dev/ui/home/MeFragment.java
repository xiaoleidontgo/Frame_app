package core.framework.dev.ui.home;


import android.os.Bundle;
import android.provider.Settings;

import core.framework.dev.R;
import lyh.library.cm.basic.fragment.LazyFragment;

/**
 * Created by lyh on 2016/10/25.
 */

public class MeFragment extends LazyFragment {

    public static MeFragment newInstance() {
        MeFragment meFragment = new MeFragment();
        Bundle bundle = new Bundle();
        meFragment.setArguments(bundle);
        return meFragment;
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
