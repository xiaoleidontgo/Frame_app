package core.framework.dev.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

import core.framework.dev.R;
import core.framework.dev.databinding.ActivityMainBinding;
import core.framework.dev.ui.home.HomeFragment;
import core.framework.dev.ui.home.IMFragment;
import core.framework.dev.ui.home.MeFragment;
import core.framework.dev.ui.home.PhotoFragment;
import core.framework.dev.ui.info.InfoCenterActivity;
import core.framework.dev.ui.photo.PhotoCardActivity;
import core.framework.dev.ui.video.PushVideoActivity;
import lyh.library.cm.basic.activity.ActivityBase;
import lyh.library.cm.component.tabbar.TabBarLayout;
import lyh.library.cm.component.tabbar.TabHandler;
import lyh.library.cm.component.tabbar.TabUiHandler;
import lyh.library.cm.utils.FragmentUtils;
import lyh.library.cm.utils.StatusBarUtils;
import lyh.library.cm.widget.animLayout.AnimLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 主界面
 */
public class MainActivity extends ActivityBase implements TabHandler {

    ActivityMainBinding mBinding;
    Fragment mCurrentFragment;

    public static void launch(Activity from) {
        Intent intent = new Intent(from, MainActivity.class);
        from.startActivity(intent);
        from.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColorID(R.color.blue);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initData();
        switchPage(0);
    }


    /**
     * 初始化底部Tab栏数据
     */
    @Override
    public void initData() {
        super.initData();
        mBinding.tabBar.setTabHandler(this);
        mBinding.animLayout.setOnMenuStatusChangeListener(isOpen -> {
            if (isOpen) {
                mBinding.rlFrameBg.setVisibility(View.VISIBLE);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                alphaAnimation.setDuration(300);
                alphaAnimation.setFillAfter(true);
                mBinding.rlFrameBg.startAnimation(alphaAnimation);
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(300);
                alphaAnimation.setFillAfter(true);
                mBinding.rlFrameBg.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mBinding.rlFrameBg.clearAnimation();
                        mBinding.rlFrameBg.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        mBinding.rlFrameBg.setOnClickListener(v -> mBinding.animLayout.hideMenu());

        //菜单事件
        mBinding.animLayout.setOnMenuItemClickListener((view, item) -> {
                    mBinding.tabBar.tryToResetNoPageTab();
                    Observable
                            .timer(300, TimeUnit.MILLISECONDS, Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe((Long) -> {
                                if (item == 0)
                                    PushVideoActivity.launch(MainActivity.this);//开启个人直播
                                else if (item == 1)
                                    InfoCenterActivity.launch(MainActivity.this);//开启资讯中心
                                else if (item == 2)
                                    PhotoCardActivity.launch(MainActivity.this);//开启卡片界面
                            });
                }
        );
    }

    /**
     * TabBar事件
     * 返回值表示此次Tab的点击事件是否有效
     * true有效 false无效
     *
     * @param tabItem
     * @param isDoubleClick
     */
    @Override
    public boolean onTabClick(TabUiHandler tabItemView, int tabItem,
                              boolean isReSelected,
                              boolean isDoubleClick) {
        Log.d(TAG, "onTabClick" + tabItem);

        if (mBinding.animLayout.getMenuStatus()) {
            mBinding.animLayout.hideMenu();
            return false;
        }

        //是否登录，未登录则去登录
//        if (tabItem == 4) {
//            LoginActivity.launch(this);
//            return false;
//        }

        if (tabItem == 2) {
            toggleArcMenu();
        } else {
            switchPage(tabItem);
        }
        return true;
    }

    /**
     * 页面切换
     *
     * @param tabItem
     */
    private void switchPage(int tabItem) {
        mCurrentFragment = FragmentUtils.switchFragment(
                getSupportFragmentManager(),
                R.id.frame_container,
                mCurrentFragment,
                tabItem == 0 ? HomeFragment.class :     //主页
                        tabItem == 1 ? IMFragment.class :     //消息页
                                tabItem == 3 ? PhotoFragment.class :     //图片页
                                        MeFragment.class,   //个人页
                null);
    }

    /**
     * 环形菜单切换状态
     */
    private void toggleArcMenu() {
        if (mBinding.animLayout != null) {
            if (mBinding.animLayout.getMenuStatus()) {
                mBinding.animLayout.hideMenu();
            } else {
                mBinding.animLayout.showMenu();
            }
        }
    }

    /**
     * 返回事件
     *
     * @return
     */
    @Override
    protected boolean processBackPressed() {

        if (mBinding.animLayout != null) {
            if (mBinding.animLayout.getMenuStatus()) {
                mBinding.animLayout.hideMenu();
                return true;
            }
        }

        if (mBinding.tabBar != null) {
            if (mBinding.tabBar.getCurrentItem() != 0) {
                mBinding.tabBar.setCurrentItem(0);
                return true;
            }
        }

        return super.processBackPressed();
    }

}
