package lyh.library.cm.basic.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lyh.library.cm.adapter.recycleview.MultiItemTypeAdapter;
import lyh.library.cm.adapter.recycleview.RVAdapter;
import lyh.library.cm.adapter.recycleview.wrapper.MultiStatusLoadMoreView;
import lyh.library.cm.adapter.recycleview.wrapper.LoadMoreWrapper;
import lyh.library.cm.component.ptr.PtrFrameLayout;
import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/10/24.
 * 默认的列表控件为RecyclerView 建议用RecyclerView去实现列表功能
 * flag-->> 暂时未处理适配器相关方法
 */

public abstract class SwipeRefreshListFragment<T extends Object> extends SwipeRefreshFragment
        implements RVAdapter.OnItemClickListener, RVAdapter.OnItemLongClickListener,
        LoadMoreWrapper.OnLoadMoreListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<T> mAdapterData;
    private LoadMoreWrapper.IMultiStatusLoadMoreView mLoadMoreView;

    @Override
    protected int getLayoutResId() {
        return R.layout.common_ui_refresh_list;
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        recyclerView = $(R.id.recyclerview);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        mAdapterData = new ArrayList<>();
        mAdapter = initAdapter(mAdapterData);
        configLayoutManager(recyclerView);
        setUpRecyclerViewEvent(recyclerView);
        setUpAdapter(recyclerView);
    }

    /**
     * 配置RecyclerView  LayoutManager...
     * 子类如果需要更改样式，需要复写此方法，默认为单列垂直
     */
    public void configLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置RecyclerView的事件
     * 点击事件如果适配器使用了MultiItemTypeAdapter　封装的类型，则有
     * 默认的事件实现，如果不是，则需要覆盖此方法进行自己的实现
     *
     * @param recyclerView
     */
    private void setUpRecyclerViewEvent(RecyclerView recyclerView) {
        if (mAdapter instanceof MultiItemTypeAdapter) {
            ((MultiItemTypeAdapter) mAdapter).setOnItemClickListener(this);
        }
    }

    /**
     * 是否可以进行下拉刷新的配置，每次用户的操作都会询问此方法，
     * 以确定自身的行为，所以可以根据具体的contetView进行复写此方法，
     * 确定在合适的时机进行刷新操作
     *
     * @param frame
     * @param content
     * @param header
     * @return true 可以进行下拉刷新  false 禁用
     */
    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return super.checkCanDoRefresh(frame, content, header);
    }

    /**
     * 是否可以进行加载更多操作，此方法，只支持RecyclerView的加载更多操作
     *
     * @return true 可以进行上拉加载 false 不支持上拉加载
     */
    protected boolean checkCanDoLoadMore() {
        return false;
    }

    /**
     * 加载更多方法执行的回调
     * isLazyLoadFirst -> 防止触发 lazyRequest() 时，同时触发 onLoadMoreRequested()
     */
    @Override
    public void onLoadMoreRequested() {
        if (isLazyLoadFirst) {
            return;
        }
    }

    /**
     * 防止加载更多回调的产生而破坏懒加载，
     * 在界面显示的时候才进行注册回调接口
     */
    @Override
    protected void onVisible() {
        super.onVisible();
        if (checkCanDoLoadMore() && mLoadMoreWrapper != null)
            if (mLoadMoreWrapper.getOnLoadMoreListener() == null)
                mLoadMoreWrapper.setOnLoadMoreListener(this);
    }

    /**
     * 加载更多时显示的布局，如果需要实现特殊效果，则复写此方法
     *
     * @return
     */
    protected LoadMoreWrapper.IMultiStatusLoadMoreView getLoadMoreView() {
        if (mLoadMoreView == null) throw new NullPointerException("LoadMoreView is null point");
        return mLoadMoreView;
    }

    /**
     * 条目点击事件
     *
     * @param view
     * @param holder
     * @param position
     */
    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * 条目长按事件
     *
     * @param view
     * @param holder
     * @param position
     * @return
     */
    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return true;
    }

    /**
     * 设置列表适配器
     *
     * @param recyclerView
     */
    protected void setUpAdapter(RecyclerView recyclerView) {
        initAdapterData(mAdapterData);
        if (mAdapter == null)
            throw new NullPointerException("the adapter is null , " +
                    "please check the method -> 'initAdapter()' " +
                    " is you override the method and return a real object ?");
        if (checkCanDoLoadMore()) {
            mLoadMoreWrapper = new LoadMoreWrapper(mAdapter);
            mLoadMoreWrapper.setLoadMoreView((View) (mLoadMoreView = new MultiStatusLoadMoreView(mActivity)));
            recyclerView.setAdapter(mLoadMoreWrapper);
        } else
            recyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化适配器数据源
     *
     * @return
     */
    public abstract void initAdapterData(List<T> adapterData);

    /**
     * 列表适配器
     */
    public abstract RecyclerView.Adapter initAdapter(List<T> data);

    /**
     * 获取适配器的数据集
     *
     * @return
     */
    public List<T> getAdapterData() {
        return mAdapterData;
    }

    /**
     * 获取适配器
     * 注意调用此方法时，请用作你自己返回的适配器类型
     * 如果使用了提供的加载更多功能，则需要用LoadMoreWrapper接受类型
     *
     * @return
     */
    public <T extends RecyclerView.Adapter> T getAdapter() {
        if (mAdapter == null)
            throw new NullPointerException("the adapter is null point... " +
                    "please check your method -> 'initAdapter() " +
                    "is return a real object for the method ? '");
        if (checkCanDoLoadMore())
            return (T) mLoadMoreWrapper;
        return (T) mAdapter;
    }


    /**
     * 获取RecyclerView
     *
     * @return
     */
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    /**
     * 数据集替换与刷新
     *
     * @param newData 新的数据集
     */
    public void replaceDataAndNotify(List<T> newData) {
        //后续添加用上DiffUtil
        getAdapterData().clear();
        getAdapterData().addAll(newData);
        getAdapter().notifyDataSetChanged();
    }

    /**
     * 增加数据集到适配器数据集中
     *
     * @param addData
     */
    public void addAllDataAndNotify(List<T> addData) {
        int startIndex = getAdapterData().size();
        getAdapterData().addAll(addData);
        getAdapter().notifyItemRangeChanged(startIndex, addData.size());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * release your object
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
