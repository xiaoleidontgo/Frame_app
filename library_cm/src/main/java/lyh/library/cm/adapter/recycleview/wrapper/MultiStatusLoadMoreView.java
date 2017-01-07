package lyh.library.cm.adapter.recycleview.wrapper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/11/23.
 * 自定义加载更多脚控件
 */
public class MultiStatusLoadMoreView extends RelativeLayout implements LoadMoreWrapper.IMultiStatusLoadMoreView {

    private LinearLayout loadingView, noNetView, failureView, noMoreView;


    public MultiStatusLoadMoreView(Context context) {
        this(context, null);
    }

    public MultiStatusLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiStatusLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_loadmore_view, this);
        loadingView = (LinearLayout) findViewById(R.id.ll_loading);
        noNetView = (LinearLayout) findViewById(R.id.ll_no_net);
        failureView = (LinearLayout) findViewById(R.id.ll_failure);
        noMoreView = (LinearLayout) findViewById(R.id.ll_no_more);
    }


    @Override
    public void onLoadMore() {
        allGone();
        loadingView.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadFailure() {
        allGone();
        failureView.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadNoNet() {
        allGone();
        noNetView.setVisibility(VISIBLE);
    }

    @Override
    public void onNoMore() {
        allGone();
        noMoreView.setVisibility(VISIBLE);
    }

    private void allGone() {
        noNetView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        failureView.setVisibility(GONE);
        noMoreView.setVisibility(GONE);
    }

    @Override
    public boolean isLoading() {
        return loadingView != null && loadingView.getVisibility() == VISIBLE;
    }
}
