package core.framework.dev.ui.video;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import core.framework.dev.R;
import core.framework.dev.bean.Feedback;
import core.framework.dev.data.http.RetrofitHelper;
import core.framework.dev.data.http.rx.XSubscriber;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.basic.fragment.SingleSwipeRefreshListFragment;
import lyh.library.cm.component.ptr.PtrFrameLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2016/11/13.
 * <p>
 * 视频评论
 */
public class LiveVideoRankFragment extends SingleSwipeRefreshListFragment<Integer> {

    public static LiveVideoRankFragment newInstance(int area_id) {
        Bundle args = new Bundle();
        args.putInt("area_id", area_id);
        LiveVideoRankFragment fragment = new LiveVideoRankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //视频号
    private int aid = -1;
    private int page = 0;
    private static final int page_size = 10;
    //api 版本号
    private int ver = 3;

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        Bundle arguments = getArguments();
        aid = arguments.getInt("area_id");
    }

    @Override
    public void initAdapterData(List<Integer> adapterData) {
        for (int i = 1; i <= 10; i++)
            adapterData.add(i);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_live_video_rank;
    }

    @Override
    protected void bindItemViewData(ViewHolder holder, Integer s, int position) {
        holder.setText(R.id.tv_rank_id, s + "")
                .setText(R.id.tv_rank_name, "小雷同学的粉丝" + s)
                .setText(R.id.tv_feed_num, "101");
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {
        startAutoRefreshDelay(100);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        super.onRefreshBegin(frame);
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> frame.refreshComplete());
    }

    private void doRequest() {
        RetrofitHelper.getFeedbackApi().getFeedback(aid, page, page_size, ver)
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new XSubscriber<Feedback>() {
                    @Override
                    public void onError(Throwable e) {
                        if (getAdapterData().size() == 0)
                            showFailureView();
                    }

                    @Override
                    public void onNext(Feedback feedback) {
                        showContentLayout();
                    }
                });
    }

}
