package lyh.library.cm.component.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import lyh.library.cm.adapter.BasePagerAdapter;
import lyh.library.cm.component.banner.view.DotIndicator;

/**
 * Created by lyh on 2016/7/13.
 * 自动轮播控件
 */
public class SlideBanner extends RelativeLayout {

    public static final String TAG = SlideBanner.class.getSimpleName();

    private PlayableViewPager mViewPager;
    private PageIndicator mIndicator;
    private AutoPlayer mPlayer;
    private ViewPager.OnPageChangeListener mListener;
    private List<String> mUrls;
    private List<ImageView> mImgs;
    private SlideBanner.BannerClickCallback mClickCallback;
    private BitmapLoader mBitmapLoader;

    public SlideBanner(Context context) {
        super(context);
        init();
    }

    public SlideBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewPager = new PlayableViewPager(getContext());
        mViewPager.addOnPageChangeListener(new PlayListener());
        this.addView(mViewPager);
        mPlayer = new AutoPlayer(mViewPager);
        mIndicator = new DotIndicator(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        this.addView((View) mIndicator, lp);
    }

    /**
     * 开始播放，默认的模式
     */
    public void startPlay() {
        mPlayer.startAndInitPlay(0, AutoPlayer.Play_Mode.play_back);
    }

    /**
     * 开始进行图片轮播，并制定开始的位置，与播放的模式
     *
     * @param start
     * @param mode
     */
    public void startPlay(int start, AutoPlayer.Play_Mode mode) {
        mPlayer.startAndInitPlay(start, mode);
    }

    /**
     * 停止播放
     */
    public void stopPlayer() {
        mPlayer.stop();
    }

    /**
     * 设置指示栏
     *
     * @param indicator
     */
    public void setIndicator(PageIndicator indicator) {
        this.mIndicator = indicator;
    }

    /**
     * 使用外界的图片加载框架加载图片
     *
     * @param bitmapLoader
     */
    public void setBitmapLoader(BitmapLoader bitmapLoader) {
        if (bitmapLoader == null) return;
        this.mBitmapLoader = bitmapLoader;
    }

    /**
     * 传进来图片网络地址，自动下载播放
     */
    public void setUrls(List<String> datas) {
        if (datas == null) return;
        this.mUrls = datas;
        mIndicator.setIndicatorNum(mUrls.size());
        mViewPager.setAdapter(new BannerAdapter());
    }

    /**
     * 传进来图片播放
     */
    public void setImages(List<ImageView> imgs) {
        if (imgs == null) return;
        this.mImgs = imgs;
        mIndicator.setIndicatorNum(imgs.size());
        mViewPager.setAdapter(new BannerAdapter());
    }

    //left | center | right
    public void setIndicatorMode(PageIndicator.MODE mode) {
        mIndicator.setIndicatorMode(mode);
    }

    //repeat | back
    public void setPlayMode(AutoPlayer.Play_Mode mode) {
        mPlayer.setPlayMode(mode);
    }

    //speed current speed is 5000ms the time should not minits the time
    public void setPlaySpeed(int speed) {
        mPlayer.setAutoPlayInterval(speed);
    }

    //if need cache image, set the file name
    public void setCacheFile(String file) {
        //TODO insert you code
    }

    public void setOnPagerChangListener(ViewPager.OnPageChangeListener l) {
        this.mListener = l;
    }

    /**
     * 事件分发时，根据用户的手势暂停，重启滚动操作
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPlayer != null) {
                    mPlayer.pause();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (mPlayer != null) {
                    mPlayer.resume();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * ViewPager的适配器
     */
    class BannerAdapter extends BasePagerAdapter {
        @Override
        public View getView(LayoutInflater inflater, final int position) {
            if (mImgs == null) {
                mImgs = new ArrayList<>();
                for (final String url : mUrls) {
                    ImageView iv = new ImageView(getContext());
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mImgs.add(iv);
                    iv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mClickCallback != null) {
                                mClickCallback.onClick(v, mViewPager.getCurrentItem(), url);
                            }
                        }
                    });
                }
            }
            if (mUrls == null) {
                return mImgs.get(position);
            } else {
                if (mBitmapLoader == null) {
                    mBitmapLoader = new BitmapLoaderImpl();
                }
                if (position < mUrls.size())
                    mBitmapLoader.loadBitmap(SlideBanner.this.getContext(), mUrls.get(position), mImgs.get(position));
                return mImgs.get(position);
            }
        }

        @Override
        public int getCount() {
            return mUrls == null ? 0 : mUrls.size();//
        }
    }

    /**
     * 滚动监听
     */
    class PlayListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mListener != null) {
                mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            mIndicator.onIndicatorScroll(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            if (mListener != null) {
                mListener.onPageSelected(position);
            }
            mIndicator.onIndicatorSelected(position);
        }

        /**
         * 有三种状态（0，1，2）。
         * state ==1的时辰默示正在滑动，state==2的时辰默示滑动完毕了，state==0的时辰默示什么都没做。
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (mListener != null) {
                mListener.onPageScrollStateChanged(state);
            }
            mPlayer.setSliding(state != 2);
        }
    }

    public BannerClickCallback getBannerClickListener() {
        return mClickCallback;
    }

    public void setBannerClickListener(BannerClickCallback l) {
        this.mClickCallback = l;
    }

    /**
     * SlideBanner的点击事件的回调处理
     */
    public interface BannerClickCallback {
        void onClick(View v, int item, String url);
    }

    /**
     * 加载图片接口
     */
    public interface BitmapLoader {
        void loadBitmap(Context context, String url, ImageView iv);
    }

    public class BitmapLoaderImpl implements BitmapLoader {
        @Override
        public void loadBitmap(Context context, String url, ImageView iv) {
            Glide.with(SlideBanner.this.getContext())
                    .load(url)
                    .centerCrop().crossFade().into(iv);
        }
    }
}
