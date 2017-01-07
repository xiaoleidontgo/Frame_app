package core.framework.dev.ui.im;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import core.framework.dev.R;
import core.framework.dev.adapter.MessageAdapter;
import lyh.library.cm.adapter.helper.SimpleItemTouchHelperCallback;
import lyh.library.cm.basic.fragment.SwipeRefreshListFragment;

/**
 * Created by lyh on 2016/10/31.
 * IM 下 消息
 */

public class MessageFragment extends SwipeRefreshListFragment<String> {

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public RecyclerView.Adapter initAdapter(List<String> data) {
        MessageAdapter messageAdapter = new MessageAdapter(mActivity, R.layout.item_im_message, data);
        ItemTouchHelper.Callback swipeItemTouchHelper = new SimpleItemTouchHelperCallback(messageAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(getRecyclerView());
        return messageAdapter;
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        super.onItemClick(view, holder, position);
        showToast("item==" + position);

    }

    @Override
    public void initAdapterData(List<String> adapterData) {
        adapterData.add("yonghuiLei");
        adapterData.add("光明之战");
        adapterData.add("血族");
        adapterData.add("奋斗在上海");
        adapterData.add("Ruik");
        adapterData.add("女神对我说");
        adapterData.add("Micheal");
        adapterData.add("joy");
        adapterData.add("小雷放学别走");
        adapterData.add("下雷同学");
        adapterData.add("听说你很屌");
        adapterData.add("下课来战");
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {

    }
}
