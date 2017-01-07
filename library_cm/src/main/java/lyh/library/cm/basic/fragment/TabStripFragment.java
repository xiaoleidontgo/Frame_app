package lyh.library.cm.basic.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import lyh.library.cm.adapter.BaseFragmentAdapter;
import lyh.library.cm.utils.LocalDisplay;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/10/25.
 */

public abstract class TabStripFragment extends LazyFragment implements ViewPager.OnPageChangeListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    protected int getLayoutResId() {
        return R.layout.common_ui_tabstrip;
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        tabLayout = $(R.id.tablayout);
        viewPager = $(R.id.viewpager);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        mFragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        if (initFragments() == null) {
            throw new NullPointerException("the initFragments() must not be return null...");
        }
        mFragments.addAll(initFragments());
        if (initTabLayoutTitles() == null) {
            throw new NullPointerException("the initTabLayoutTitles() must not be return null...");
        }
        mTitles.addAll(initTabLayoutTitles());
        initViewPager(viewPager);
        configTabLayout(tabLayout);
        initTabLayout();
    }

    /**
     * 配置TabLayout
     */
    public void configTabLayout(TabLayout tabLayout) {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 设置TabLayout高度
     * 高度会被转化为dp
     *
     * @param height
     */
    public void setTabLayoutHeight(int height) {
        tabLayout.getLayoutParams().height = LocalDisplay.dp2px(height);
        tabLayout.setLayoutParams(tabLayout.getLayoutParams());
    }


    /**
     * 初始化viewpager
     */
    private void initViewPager(ViewPager viewPager) {
        TabStripViewPagerAdapter mAdapter = new TabStripViewPagerAdapter(getChildFragmentManager(), getFragments(), getTabLayoutTitles());
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(mAdapter);
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
//        for (String title : initTabLayoutTitles()) {
//            tabLayout.addTab(tabLayout.newTab().setText(title));
//        }
    }

    /**
     * viewpager内部的Fragment
     *
     * @return
     */
    public abstract List<Fragment> initFragments();

    /**
     * 设置TabLayout标题
     */
    public abstract List<String> initTabLayoutTitles();

    /**
     * 获取内部碎片集合
     *
     * @return
     */
    public List<Fragment> getFragments() {
        if (mFragments == null) mFragments = initFragments();
        return mFragments;
    }

    /**
     * 获取标题集合
     *
     * @return
     */
    public List<String> getTabLayoutTitles() {
        if (mTitles == null) mTitles = initTabLayoutTitles();
        return mTitles;
    }


    /**
     * ViewPager +Fragment 适配器
     */
    class TabStripViewPagerAdapter extends BaseFragmentAdapter {

        public TabStripViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm, fragments);
        }

        public TabStripViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm, fragments, titles);
        }

    }

    /**
     * ViewPager事件监听
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
