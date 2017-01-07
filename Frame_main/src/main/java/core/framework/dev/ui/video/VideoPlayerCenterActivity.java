package core.framework.dev.ui.video;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.framework.dev.R;
import core.framework.dev.adapter.LivePlayerCenterPagerAdapter;
import core.framework.dev.base.BaseActivity;
import core.framework.dev.config.Constant;
import core.framework.dev.databinding.ActivityLiveVideoPlayerCenterBinding;
import core.framework.dev.media.AndroidMediaController;
import lyh.library.cm.basic.activity.ActivityBase;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 直播播放中心
 */
public class VideoPlayerCenterActivity extends BaseActivity {

    public static void launch(Activity from, String playUrl, int area_id) {
        Intent intent = new Intent(from, VideoPlayerCenterActivity.class);
        intent.putExtra("play_url", playUrl);
        intent.putExtra("area_id", area_id);
        from.startActivity(intent);
    }

    ActivityLiveVideoPlayerCenterBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_live_video_player_center);

        Intent intent = getIntent();
        if (intent == null) return;
        String play_url = intent.getStringExtra("play_url");
        int area_id = intent.getIntExtra("area_id", Constant.INVALID_CODE);

        //init viewpager
        initViewPager(area_id);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewpager);
        // init player
        initPlayer(play_url);

    }

    private void initPlayer(String play_url) {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        AndroidMediaController mediaController = new AndroidMediaController(this, false);
        mBinding.videoView.setMediaController(mediaController);
        mBinding.videoView.setHudView(mBinding.hudView);
        mBinding.videoView.setVideoPath(play_url);
        mBinding.videoView.start();
    }

    private void initViewPager(int area_id) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(LiveVideoFeedbackFragment.newInstance(area_id));
        fragments.add(LiveVideoRankFragment.newInstance(area_id));
        fragments.add(LiveVideoRankFragment.newInstance(area_id));
        fragments.add(LiveVideoRankFragment.newInstance(area_id));

        TabLayout tabLayout = mBinding.tabLayout;
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        List<String> titles = Arrays.asList(getResources().getStringArray(R.array.array_live_channes));

        LivePlayerCenterPagerAdapter adapter = new LivePlayerCenterPagerAdapter(
                getSupportFragmentManager(), fragments, titles);
        mBinding.viewpager.setAdapter(adapter);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(v -> finish());
        mBinding.toolbar.setTitle("小雷同学的直播间");
        getSupportActionBar().setTitle("小雷同学的直播间");

    }

    boolean mBackPressed;

    @Override
    public void onBackPressed() {
        mBackPressed = true;

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBackPressed || !mBinding.videoView.isBackgroundPlayEnabled()) {
            mBinding.videoView.stopPlayback();
            mBinding.videoView.release(true);
            mBinding.videoView.stopBackgroundPlay();
        } else {
            mBinding.videoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }


}
