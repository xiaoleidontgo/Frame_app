package lyh.library.cm.adapter.expandable;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lyh.library.cm.adapter.recycleview.MultiItemTypeAdapter;
import lyh.library.cm.adapter.recycleview.base.ItemViewDelegate;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.utils.ToastUtils;

/**
 * RecyclerView 实现折叠效果 适配器
 *
 * @param <TG> 父条目对应实体数据
 * @param <TC> 子条目对应实体数据
 *             <p>flag-->>
 *             还需实现的效果：当点击一个条目后，如果父条目在最底下
 *             看不到子条目 顺滑的将父条目滑动上一段距离，展示子条目
 */
public abstract class ExpandableAdapter<TG, TC> extends MultiItemTypeAdapter<Object> {

    /**
     * 存储所有数据集
     */
    private Map<Group, ArrayList<Child>> mGroupAndChildData;

    /**
     * 父条目状态监听
     */
    private GroupStateChangeListener mGroupStateChangeListener;

    //view type
    private static final int VIEW_TYPE_SECTION = 0x01;
    private static final int VIEW_TYPE_ITEM = 0x01 >> 1;

    private int parentLayoutId;
    private int chileLayoutId;

    public ExpandableAdapter(Context context, List<Object> datas,
                             final int childSpan, int parentLayoutId, int chileLayoutId) {
        super(context, datas);
        mContext = context;
        this.parentLayoutId = parentLayoutId;
        this.chileLayoutId = chileLayoutId;

        mGroupAndChildData = new HashMap<>();
        list2Map();
        map2List();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, childSpan);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return isGroup(position) ? childSpan : 1;
            }
        });

        addItemViewDelegate(VIEW_TYPE_SECTION, new ParentItem<TG>());
        addItemViewDelegate(VIEW_TYPE_ITEM, new ChildItem<TC>());

    }

    public ExpandableAdapter(Context context, List<Object> datas,
                             int parentLayoutId, int chileLayoutId) {
        this(context, datas, 1, parentLayoutId, chileLayoutId);
    }

    /**
     * 按 Map<Group<TG>, ArrayList<Child<TC>>> 保存进一组数据
     *
     * @param groupMaps
     */
    public void putGroupData(Map<Group, ArrayList<Child>> groupMaps) {
        if (groupMaps == null) return;
        mGroupAndChildData = groupMaps;
        map2List();
        notifyDataSetChanged();
    }

    /**
     * 将原始数据加工成分组数据
     */

    public void list2Map() {
        ArrayList<Child> childItems = null;
        Group group = null;
        Child item = null;
        for (int i = 0; i < mDatas.size(); i++) {
            Object data = mDatas.get(i);

            if (data instanceof Group) {
                if (i != 0) {
                    mGroupAndChildData.put(group, childItems);
                }
                group = (Group) data;
                childItems = new ArrayList<>();
            } else if (data instanceof Child) {

                item = (Child) data;
                childItems.add(item);
            }

            if (i == mDatas.size() - 1) {
                mGroupAndChildData.put(group, childItems);
            }

        }

    }

    /**
     * map集合数据转化为list数据
     */

    public void map2List() {
        mDatas.clear();
        for (Map.Entry<Group, ArrayList<Child>> data : mGroupAndChildData.entrySet()) {

            if (data.getKey().isExpanded) {
                mDatas.add(data.getKey());
                for (Child item : data.getValue()) {
                    mDatas.add(item);
                }
            } else {
                mDatas.add(data.getKey());
            }
        }

    }

    /**
     * 条目数据是否为父条目
     *
     * @param position
     * @return
     */
    public boolean isGroup(int position) {
        return mDatas.get(position) instanceof Group;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        if (isGroup(position)) {

            onGroupClick(view, holder, (Group) mDatas.get(position), position);

            if (mGroupStateChangeListener != null) {
                Group s = (Group) mDatas.get(position);
                mGroupStateChangeListener.onSectionStateChanged(s, false);
            }
        } else {
            onChildClick(view, holder, (Child) mDatas.get(position), position);
        }
    }


    /**
     * 父条目
     *
     * @param <TG> 父条目数据
     */
    class ParentItem<TG> implements ItemViewDelegate<Object> {

        @Override
        public int getItemViewLayoutId() {
            return parentLayoutId;
        }

        @Override
        public boolean isForViewType(Object item, int position) {
            return isGroup(position);
        }

        @Override
        public void convert(ViewHolder holder, Object t, int position) {
            if (t instanceof Group)
                convertParent(holder, (Group) t, position);
        }
    }

    /**
     * 子条目
     *
     * @param <TC> 子条目数据
     */
    class ChildItem<TC> implements ItemViewDelegate<Object> {

        @Override
        public int getItemViewLayoutId() {
            return chileLayoutId;
        }

        @Override
        public boolean isForViewType(Object item, int position) {
            return !isGroup(position);
        }

        @Override
        public void convert(ViewHolder holder, Object t, int position) {
            if (t instanceof Child)
                convertChild(holder, (Child) t, position);
        }
    }

    /**
     * 对父条目进行赋值
     *
     * @param holder
     * @param t
     * @param position
     */
    public abstract void convertParent(ViewHolder holder, Group t, int position);

    /**
     * 对子条目进行赋值
     *
     * @param holder
     * @param t
     * @param position
     */
    public abstract void convertChild(ViewHolder holder, Child t, int position);

    /**
     * 父条目点击
     *
     * @param view
     * @param holder
     * @param group
     * @param position
     */
    final private void onGroupClick(View view, RecyclerView.ViewHolder holder, Group group, int position) {
        mDatas.set(position, !group.isExpanded);
        ArrayList<Child> items = mGroupAndChildData.get(group);
        group.isExpanded = !group.isExpanded;
        mGroupAndChildData.put(group, items);
        map2List();
        notifyDataSetChanged();

    }

    /**
     * 子条目点击
     *
     * @param view
     * @param holder
     * @param item
     * @param position
     */
    public void onChildClick(View view, RecyclerView.ViewHolder holder, Child item, int position) {
        ToastUtils.showShort("onChildClick");
    }

    /**
     * 设置父条目收缩折叠状态监听
     *
     * @param l
     */
    public void setGroupStateChangeListener(GroupStateChangeListener l) {
        mGroupStateChangeListener = l;
    }

    /**
     * 事件回调
     */
    public interface ExpandableCallback {
        void onGroupClick(View view, RecyclerView.ViewHolder holder, Group group, int position);

        void onChildClick(View view, RecyclerView.ViewHolder holder, Child item, int position);
    }


}
