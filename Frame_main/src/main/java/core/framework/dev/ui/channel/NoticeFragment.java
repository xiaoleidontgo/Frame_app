package core.framework.dev.ui.channel;

import android.os.Bundle;

import core.framework.dev.R;
import lyh.library.cm.basic.fragment.LazyFragment;

/**
 * Created by lyh on 2016/12/13.
 * 通知页
 */
public class NoticeFragment extends LazyFragment {

    public static NoticeFragment newInstance() {
        Bundle args = new Bundle();
        NoticeFragment fragment = new NoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notice;
    }
}
