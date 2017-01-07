package core.framework.dev.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.framework.dev.R;
import core.framework.dev.ui.im.ContactFragment;
import core.framework.dev.ui.im.MessageFragment;
import core.framework.dev.ui.im.DynamicSpaceFragment;
import lyh.library.cm.basic.fragment.TabStripFragment;

/**
 * Created by lyh on 2016/10/25.
 */

public class IMFragment extends TabStripFragment {

    public static IMFragment newInstance() {
        IMFragment imFragment = new IMFragment();
        Bundle bundle = new Bundle();
        imFragment.setArguments(bundle);
        return imFragment;
    }

    private List<Fragment> mFragments;

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
    }

    @Override
    public List<Fragment> initFragments() {
        mFragments = new ArrayList<>();

        //消息
        MessageFragment messageFragment = MessageFragment.newInstance();
        //联系人
        ContactFragment contactFragment = ContactFragment.newInstance();
        //动态空间
        DynamicSpaceFragment dynamicSpaceFragment = DynamicSpaceFragment.newInstance();

        mFragments.add(messageFragment);
        mFragments.add(contactFragment);
        mFragments.add(dynamicSpaceFragment);
        return mFragments;
    }

    @Override
    public List<String> initTabLayoutTitles() {
        List mTitles = Arrays.asList(mActivity.getResources().getStringArray(R.array.array_im_channes));
        return mTitles;
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {

    }
}
