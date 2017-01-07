package lyh.library.cm.basic.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lyh.library.cm.adapter.ItemData;
import lyh.library.cm.adapter.ItemViewProvider;
import lyh.library.cm.adapter.MultiItemAdapter;

/**
 * Created by lyh on 2017/1/2.
 * 多条目类型界面直接继承此类
 * <p>
 * 在子类对不同的Item进行注册
 * 一个item对应一个Provider
 */

public abstract class MultiItemSwipeRefreshListFragment extends SwipeRefreshListFragment<ItemData> {

    private MultiItemAdapter multiItemAdapter;

    /**
     * 保存的条目类型与条目Provider
     */
    private HashMap<String, ItemViewProvider> items;

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        items = new HashMap<>();
        onRegisterItems();
    }

    /**
     * 获取适配器
     *
     * @return
     */
    protected MultiItemAdapter getMultiAdapter() {
        return multiItemAdapter;
    }

    /**
     * 在此时进行条目类型的注册
     */
    public abstract void onRegisterItems();

    /**
     * 一种类型的item对应一种实体数据类型，对应一个Provider
     * <p>
     *
     * @param item
     */
    protected void registerItem(Class<?> item, ItemViewProvider provider) {
        items.put(item.getName(), provider);
    }

    /**
     * 添加一条数据
     *
     * @param addData
     * @param <T>
     */
    public <T> void addMultiItemData(T addData) {
        getAdapterData().add(new ItemData(addData));
        getAdapter().notifyItemInserted(getAdapterData().size() - 1);
    }

    public <T> void addItemData(T addData) {
        getAdapterData().add(new ItemData(addData));
    }

    public <T> void addItemDatas(List<T> addDatas) {
        ArrayList<ItemData> datas = new ArrayList<>(addDatas.size());
        for (T t : addDatas) {
            datas.add(new ItemData(t));
        }
        getAdapterData().addAll(datas);
    }

    /**
     * 添加一组数据
     *
     * @param addDatas
     * @param <T>
     */
    public <T> void addMultiItemDatas(List<T> addDatas) {
        int startIndex = getAdapterData().size();
        ArrayList<ItemData> datas = new ArrayList<>(addDatas.size());
        for (T t : addDatas) {
            datas.add(new ItemData(t));
        }
        getAdapterData().addAll(datas);
        getAdapter().notifyItemRangeChanged(startIndex, datas.size());
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<ItemData> data) {
        multiItemAdapter = new MultiItemAdapter(mActivity, data, items);
        items = null;
        return multiItemAdapter;
    }


}
