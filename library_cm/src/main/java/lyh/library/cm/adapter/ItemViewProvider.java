package lyh.library.cm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2017/1/3.
 */

public interface ItemViewProvider<T> {

    int itemLayoutResId();

    void bindItemData(ViewHolder holder, T itemData, int position);

    void onClick(View view, RecyclerView.ViewHolder holder, T itemData, int position);

    boolean onLongClick(View view, RecyclerView.ViewHolder holder, T itemData, int position);
}
