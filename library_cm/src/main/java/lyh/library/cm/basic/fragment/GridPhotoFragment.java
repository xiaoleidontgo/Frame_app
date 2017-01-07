package lyh.library.cm.basic.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lyh on 2016/10/25.
 * 图片列表形式显示 类似相册界面的默认实现
 */

public abstract class GridPhotoFragment extends SwipeRefreshListFragment {

    @Override
    public void configLayoutManager(RecyclerView recyclerView) {
        super.configLayoutManager(recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, gridSpanCount());
        recyclerView.setLayoutManager(layoutManager);
    }


    /**
     * 列表栏目数
     *
     * @return 默认为3
     */
    public int gridSpanCount() {
        return 3;
    }
}
