package core.framework.dev.adapter.provider;

import java.util.Date;

import core.framework.dev.R;
import lyh.library.cm.adapter.AbsItemViewProvider;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.utils.DateUtil;

/**
 * Created by lyh on 2017/1/3.
 */

public class ZhihuTitleProvider extends AbsItemViewProvider<String> {

    @Override
    public int itemLayoutResId() {
        return R.layout.item_zhihu_time;
    }

    @Override
    public void bindItemData(ViewHolder holder, String itemData, int position) {
        if (itemData != null && !itemData.equals("")) {
            Date date = DateUtil.stringToDate(itemData, DateUtil.DatePattern.ONLY_DAY_);
            String s = DateUtil.dateToString(date, DateUtil.DatePattern.ONLY_DAY_C);
            holder.setText(R.id.tv_zhihu_time, s);
        }
    }

}
