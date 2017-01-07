package lyh.library.cm.component.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lyh on 2016/7/14.
 */
public class PlayableViewPager extends ViewPager implements AutoPlayer.Playable {

    public PlayableViewPager(Context context) {
        super(context);
    }

    public PlayableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void playTo(int pageTo) {
        if (getAdapter() == null || getAdapter().getCount() <= 1) return;
        if (pageTo < 0 || pageTo > getTotal()) pageTo = 0;
        setCurrentItem(pageTo, true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }

    @Override
    public void playNext() {
        this.setCurrentItem(getCurrentItem() + 1, true);
    }

    @Override
    public void playPrevious() {
        this.setCurrentItem(getCurrentItem() - 1, true);
    }

    @Override
    public int getTotal() {
        return getAdapter().getCount();
    }

    @Override
    public int getCurrent() {
        return getCurrentItem();
    }
}
