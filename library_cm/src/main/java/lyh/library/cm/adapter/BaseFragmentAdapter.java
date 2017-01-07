package lyh.library.cm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lyh on 2016/8/15.
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
        if (fragments == null) {
            throw new NullPointerException("the list of the fragment can not be null");
        }
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        if (fragments == null) {
            throw new NullPointerException("the list of the fragment can not be null");
        }
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? null : mTitles.get(position);
    }
}
