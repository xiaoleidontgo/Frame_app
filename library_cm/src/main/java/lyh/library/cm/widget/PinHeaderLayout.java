package lyh.library.cm.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import lyh.library.cm.adapter.expandable.ExpandableAdapter;

/**
 * Created by lyh on 2016/11/3.
 * 固定头部容器的实现
 * <p>
 * 使用，在布局头上设置好需要固定的view
 */
public class PinHeaderLayout extends RelativeLayout {

    private View mHeaderView;
    private RecyclerView recyclerView;
    private ScrollListener scrollListener;
    private int mCurrentItem;

    public PinHeaderLayout(Context context) {
        this(context, null);
    }

    public PinHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCurrentItem(0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() < 2) {
            throw new IllegalArgumentException("2 child is must that the group need");
        }

        mHeaderView = getChildAt(1);
        recyclerView = (RecyclerView) getChildAt(0);
        if (scrollListener == null) {
            scrollListener = new ScrollListener();
            recyclerView.addOnScrollListener(scrollListener);
        }
        mHeaderView.setVisibility(VISIBLE);

    }

    public boolean isHeaderType(int item) {
        if (!(recyclerView.getAdapter() instanceof ExpandableAdapter)) return false;
        ExpandableAdapter adapter = (ExpandableAdapter) recyclerView.getAdapter();
        return adapter.isGroup(item);
    }

    public void setCurrentItem(int item) {
        mCurrentItem = item;
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    int distance = 0;

    class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager l = (LinearLayoutManager) layoutManager;
                int visibleItem = l.findFirstVisibleItemPosition();

                distance += dy;

                if (distance > mHeaderView.getHeight()) {
                    distance = mHeaderView.getHeight();
                } else if (distance < -mHeaderView.getHeight()) {
                    distance = -mHeaderView.getHeight();
                }

                if (dy > 0) {//上滑
                    if (visibleItem + 1 < recyclerView.getAdapter().getItemCount()) {
                        if (isHeaderType(visibleItem + 1) && Math.abs(distance) <= mHeaderView.getHeight()) {
                            mHeaderView.layout(mHeaderView.getLeft(), mHeaderView.getTop() - dy,
                                    mHeaderView.getRight(), mHeaderView.getBottom() - dy);
                        }
                    }
                } else {//下滑
                    if (visibleItem - 1 >= 0) {
                        if (isHeaderType(visibleItem - 1) && Math.abs(distance) <= mHeaderView.getHeight()) {
                            mHeaderView.layout(mHeaderView.getLeft(), mHeaderView.getTop() - dy,
                                    mHeaderView.getRight(), mHeaderView.getBottom() - dy);
                        }
                    }
                }
            }
        }
    }

}