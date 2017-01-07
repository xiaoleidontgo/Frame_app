package lyh.library.cm.adapter.recycleview.wrapper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.adapter.recycleview.utils.WrapperUtils;
import lyh.library.cm.utils.NetStatusUtils;


/**
 * Created by zhy on 16/6/23.
 * Changed by lyh on 16/11/23
 * <p>
 * Changed tips :
 * 实现了加载更多时，多种状态的展示。
 * 会自动根据网络状态显示，并提供了接口供外界调用
 */
public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private Context mContext;
    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    public LoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();
        if (viewType == ITEM_TYPE_LOAD_MORE) {
            ViewHolder holder;
            if (mLoadMoreView != null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /**
         * 加载更多回调触发
         */
        if (isShowLoadMore(position)) {
            if (mOnLoadMoreListener != null) {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            if (mLoadMoreView instanceof IMultiStatusLoadMoreView) {
                ((IMultiStatusLoadMoreView) mLoadMoreView).onLoadMore();
            }
            checkNetworkStatus();
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    /**
     * 检查网络状态 从而显示对应的加载更多view的状态
     */
    private void checkNetworkStatus() {
        if (!NetStatusUtils.isNetWorkConnected(mContext)) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((IMultiStatusLoadMoreView) mLoadMoreView).onLoadNoNet();
                }
            }, 1000);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                if (isShowLoadMore(position)) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition())) {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p =
                    (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        if (mLoadMoreView instanceof IMultiStatusLoadMoreView) {
            mLoadMoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IMultiStatusLoadMoreView multiLoadMore = (IMultiStatusLoadMoreView) LoadMoreWrapper.this.mLoadMoreView;
                    if (!multiLoadMore.isLoading()) {
                        multiLoadMore.onLoadMore();
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMoreRequested();
                        }
                        checkNetworkStatus();
                    }
                }
            });
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }

    /**
     * 加载更多失败
     */
    public void onLoadFailure() {
        if (mLoadMoreView instanceof IMultiStatusLoadMoreView) {
            ((IMultiStatusLoadMoreView) mLoadMoreView).onLoadFailure();
        }
    }

    /**
     * 加载没有更多数据
     */
    public void onNoMore() {
        if (mLoadMoreView instanceof IMultiStatusLoadMoreView) {
            ((IMultiStatusLoadMoreView) mLoadMoreView).onNoMore();
        }
    }

    /**
     * 加载进来的LoadMoreView，如果需要实现多种状态的展示，则需要实现此接口
     */
    public interface IMultiStatusLoadMoreView {

        /**
         * 加载更多时
         */
        void onLoadMore();

        /**
         * 加载更多失败时
         */
        void onLoadFailure();

        /**
         * 加载更多没有网络时
         */
        void onLoadNoNet();

        /**
         * 没有更多数据时
         */
        void onNoMore();

        /**
         * 是否正在加载
         *
         * @return
         */
        boolean isLoading();

    }

}
