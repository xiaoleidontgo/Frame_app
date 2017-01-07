package core.framework.dev.ui.home;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.framework.dev.R;
import core.framework.dev.config.Constant;
import core.framework.dev.databinding.FragmentHomeBinding;
import core.framework.dev.ui.channel.NoticeFragment;
import core.framework.dev.ui.common.LoginActivity;
import core.framework.dev.ui.recommed.Home_RecommedFragment;
import core.framework.dev.ui.video.Home_LiveInfoFragment;
import core.framework.dev.ui.zhihu.Home_ZhiHuBeforeFragment;
import lyh.library.cm.basic.fragment.TabStripFragment;
import lyh.library.cm.utils.SPUtils;
import lyh.library.cm.widget.dropdown.DropDownLayout;

/**
 * Created by lyh on 2016/10/25.
 * 主页
 */

public class HomeFragment extends TabStripFragment {

    FragmentHomeBinding mBinding;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    //打开下拉菜单
    private LinearLayout llDropDown;
    private DropDownLayout dropDownLayout;
    private List<Fragment> mFragments;
    //用户信息展示
    private LinearLayout llUserInfo;
    private ImageView ivUserAvater;
    private TextView tvUserName;

    private AppBarLayout appBarLayout;

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        llUserInfo = $(R.id.ll_user_info);
        ivUserAvater = $(R.id.iv_user_avatar);
        tvUserName = $(R.id.tv_user_name);
        llDropDown = $(R.id.ll_drop_down);
        dropDownLayout = $(R.id.drop_down_layout);
        appBarLayout = $(R.id.app_bar);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        initDropDownMenu();
        //用户信息事件
        llUserInfo.setOnClickListener(v -> {
            if (!(boolean) SPUtils.get(mActivity, Constant.IS_LOGIN, false)) {
                LoginActivity.launch(mActivity);
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void configTabLayout(TabLayout tabLayout) {
        super.configTabLayout(tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    public List<Fragment> initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(Home_LiveInfoFragment.newInstance());
        mFragments.add(Home_ZhiHuBeforeFragment.newInstance());
        mFragments.add(Home_RecommedFragment.newInstance());
        mFragments.add(NoticeFragment.newInstance());
        mFragments.add(NoticeFragment.newInstance());
        return mFragments;
    }

    @Override
    public List<String> initTabLayoutTitles() {
        return Arrays.asList(mActivity.getResources().getStringArray(R.array.array_main_channes));
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {

    }

    //下拉菜单初始化
    private void initDropDownMenu() {
        List<View> pages = new ArrayList<>();
        pages.add(LayoutInflater.from(mActivity).inflate(R.layout.layout_partition, null));
        dropDownLayout.setPages(pages);
        llDropDown.setOnClickListener(v -> {
            if (dropDownLayout.hasOpenPage()) {
                dropDownLayout.closePageAt(dropDownLayout.getOpenPageIndex());
            } else {
                dropDownLayout.openPageAt(0);
            }
        });
    }

}
