package lyh.library.cm.basic.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import lyh.library.cm.adapter.recycleview.RVAdapter;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2016/11/21.
 * 极限列表展示，目前只适用于单个条目<just for simple & easy & fast></>
 */
public abstract class SingleSwipeRefreshListFragment<T> extends SwipeRefreshListFragment<T> {

    @Override
    public RecyclerView.Adapter initAdapter(List<T> data) {
        return new UltraListFragmentAdapter(mActivity, getItemLayoutId(), data);
    }

    /**
     * 条目列表
     *
     * @return
     */
    protected abstract int getItemLayoutId();

    /**
     * 绑定数据到条目列表
     *
     * @param holder   条目复用Holder
     * @param t        条目数据对象
     * @param position 条目
     */
    protected abstract void bindItemViewData(ViewHolder holder, T t, int position);

    /**
     * 列表适配器源
     */
    class UltraListFragmentAdapter extends RVAdapter<T> {

        public UltraListFragmentAdapter(Context context, int layoutId, List<T> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, T t, int position) {
            SingleSwipeRefreshListFragment.this.bindItemViewData(holder, t, position);
        }
    }


}
