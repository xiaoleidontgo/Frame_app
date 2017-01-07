package lyh.library.cm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by lyh on 2017/1/4.
 */

public abstract class AbsItemViewProvider<T> implements ItemViewProvider<T> {

    @Override
    public void onClick(View view, RecyclerView.ViewHolder holder, T itemData, int position) {

    }

    @Override
    public boolean onLongClick(View view, RecyclerView.ViewHolder holder, T itemData, int position) {
        return false;
    }
}
