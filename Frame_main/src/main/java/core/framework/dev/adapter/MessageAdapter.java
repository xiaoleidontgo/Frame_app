package core.framework.dev.adapter;

import android.content.Context;

import java.util.Collections;
import java.util.List;

import core.framework.dev.R;
import lyh.library.cm.adapter.helper.ItemTouchHelperAdapterCallback;
import lyh.library.cm.adapter.recycleview.RVAdapter;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2016/10/31.
 */
public class MessageAdapter extends RVAdapter<String> implements ItemTouchHelperAdapterCallback {

    public MessageAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tv_name, s);
    }

    @Override
    public void onItemDismiss(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDatas, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

}
