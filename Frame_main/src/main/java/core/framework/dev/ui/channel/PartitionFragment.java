package core.framework.dev.ui.channel;

import java.util.List;

import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.basic.fragment.SingleSwipeRefreshListFragment;

/**
 * Created by lyh on 2016/12/11.
 * 频道分类页
 */
public class PartitionFragment extends SingleSwipeRefreshListFragment<String> {

    @Override
    protected int getItemLayoutId() {
        return 0;
    }

    @Override
    protected void bindItemViewData(ViewHolder holder, String s, int position) {

    }

    @Override
    public void initAdapterData(List<String> adapterData) {

    }

    @Override
    public void lazyRequest() {

    }

}
