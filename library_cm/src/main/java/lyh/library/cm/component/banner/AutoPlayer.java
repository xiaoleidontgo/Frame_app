package lyh.library.cm.component.banner;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by lyh on 2016/7/14.
 * 控制轮播图的滚动，与其中图片的下载
 * thanks @huqiu.lhq
 */
public class AutoPlayer {

    //播放的模式
    public enum Play_Mode {
        repeat_start, play_back
    }

    public interface Playable {

        void playTo(int pageTo);

        void playNext();

        void playPrevious();

        int getTotal();

        int getCurrent();

    }

    private Handler mHandler;
    private Runnable mPlayTask;
    private Play_Mode mMode = Play_Mode.play_back;
    private int mTimeInterval = 5000;//播放间隔
    private Playable mPlayable;

    private boolean mPlaying = false;//记录是否正在翻页
    private boolean mPause = false;//记录是否暂停中
    private boolean mSliding = false;//记录当前ViewPager是在翻页中

    public AutoPlayer(Playable playable) {
        this.mPlayable = playable;
    }

    /**
     * 控制播放相关方法
     */

    //开启轮播
    public void start() {
        if (mPlaying) return;
        mPlaying = true;
        mPause = false;
        if (mHandler != null)
            mHandler.postDelayed(mPlayTask, mTimeInterval);
    }

    //停止轮播
    public void stop() {
        if (!mPlaying) {
            return;
        }
        mPlaying = false;
        mPause = true;
        if (mHandler != null)
            mHandler.removeCallbacks(mPlayTask);
    }

    //暂停
    public void pause() {
        mPause = true;
        //下面的语句可以注释掉
        if (mHandler != null)
            mHandler.removeCallbacks(mPlayTask);
    }

    //重新可见
    public void resume() {
        mPause = false;
        //下面的语句可以注释掉
        if (mHandler != null)
            mHandler.postDelayed(mPlayTask, mTimeInterval);
    }

    public void setSliding(boolean status) {
        mSliding = status;
    }

    /**
     * SlideBanner一开始会调用此方法，启动。
     *
     * @param start
     * @param mode
     */
    public void startAndInitPlay(int start, AutoPlayer.Play_Mode mode) {
        this.mMode = mode;
        if (mPlaying || mPlayable.getTotal() <= 1
                || start < 0 || start > mPlayable.getTotal()) {
            return;
        }

        playTo(start);

        mHandler = new Handler(Looper.myLooper());
        mPlayTask = new Runnable() {
            @Override
            public void run() {
                if (mPlaying) {
                    if (!mPause || !mSliding) {
                        playNextFrame();
                    }
                    mHandler.postDelayed(mPlayTask, mTimeInterval);
                }
            }
        };
        start();
    }

    public void playTo(int pageTo) {
        mPlayable.playTo(pageTo);
    }

    public void playNext() {
        mPlayable.playNext();
    }

    public void playPresivious() {
        mPlayable.playPrevious();
    }

    /**
     * 重复，与往返模式下的播放实现
     */
    private boolean toNext = true;

    public void playNextFrame() {

        if (mMode == Play_Mode.play_back) {

            if (mPlayable.getCurrent() == mPlayable.getTotal() - 1) {
                toNext = false;
            }
            if (mPlayable.getCurrent() == 0) {
                toNext = true;
            }
            if (toNext) {
                playNext();
            } else {
                playPresivious();
            }
        } else if (mMode == Play_Mode.repeat_start) {
            if (mPlayable.getCurrent() == mPlayable.getTotal() - 1) {
                playTo(0);
            } else {
                playNext();
            }

        }
    }

    /**
     * 控制播放相关参数
     */
    public AutoPlayer setAutoPlayInterval(int timeInterval) {
        this.mTimeInterval = timeInterval;
        return this;
    }

    public AutoPlayer setPlayMode(Play_Mode mode) {
        this.mMode = mode;
        return this;
    }

}
