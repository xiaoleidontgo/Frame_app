package lyh.library.cm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lyh.library.cm.adapter.recycleview.MultiItemTypeAdapter;
import lyh.library.cm.adapter.recycleview.base.ItemViewDelegate;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2017/1/4.
 */

public class MultiItemAdapter extends MultiItemTypeAdapter<ItemData> {

    private HashMap<String, ItemViewProvider> mItems;

    public MultiItemAdapter(Context context, List<ItemData> datas, HashMap<String, ItemViewProvider> items) {
        super(context, datas);
        this.mItems = items;
        Log.d("", "-------------------条目类型-------------------------------");
        for (Map.Entry<String, ItemViewProvider> data : mItems.entrySet()) {
            String key = data.getKey();
            ItemViewProvider value = data.getValue();
            addItemViewDelegate(creatItem(key, value));
            Log.d("", key);
        }
        Log.d("", "---------------------------------------------------------");
    }

    /**
     * 添加一条数据
     *
     * @param addData
     * @param <T>
     */
    public <T> void addItemDataNotify(T addData) {
        mDatas.add(new ItemData(addData));
        notifyItemInserted(mDatas.size() - 1);
    }

    /**
     * 添加一组数据
     *
     * @param addDatas
     * @param <T>
     */
    public <T> void addItemDatasNotify(List<T> addDatas) {
        int startIndex = mDatas.size();
        ArrayList<ItemData> datas = new ArrayList<>(addDatas.size());
        for (T t : addDatas) {
            datas.add(new ItemData(t));
        }
        mDatas.addAll(datas);
        notifyItemRangeChanged(startIndex, datas.size());
    }

    /**
     * 动态创建多条目类型
     *
     * @param provider
     * @return
     */
    public <T> ItemViewDelegate creatItem(final String typeClassName, final ItemViewProvider<T> provider) {

        ItemViewDelegate<ItemData> itemViewDelegate = new ItemViewDelegate<ItemData>() {

            @Override
            public int getItemViewLayoutId() {
                return provider.itemLayoutResId();
            }

            @Override
            public boolean isForViewType(ItemData item, int position) {
                Log.d("", typeClassName + "--->" + item.data.getClass().getName());
                return typeClassName.equals(item.data.getClass().getName());
            }

            @Override
            public void convert(ViewHolder holder, ItemData itemData, int position) {
                provider.bindItemData(holder, (T) itemData.data, position);
            }
        };

        return itemViewDelegate;
    }

    @Override
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        super.setListener(parent, viewHolder, viewType);
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                ItemData itemData = mDatas.get(position);
                for (Map.Entry<String, ItemViewProvider> data : mItems.entrySet()) {
                    String key = data.getKey();
                    if (key.equals(itemData.data.getClass().getName())) {
                        data.getValue().onClick(v, viewHolder, mDatas.get(position).data, position);
                        return;
                    }
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = viewHolder.getAdapterPosition();
                ItemData itemData = mDatas.get(position);
                for (Map.Entry<String, ItemViewProvider> data : mItems.entrySet()) {
                    String key = data.getKey();
                    if (key.equals(itemData.data.getClass().getName())) {
                        return data.getValue().onLongClick(v, viewHolder, mDatas.get(position).data, position);
                    }
                }
                return false;
            }
        });
    }

}
