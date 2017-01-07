package core.framework.dev.adapter;

import android.content.Context;

import java.util.List;

import core.framework.dev.R;
import core.framework.dev.bean.ZhiHuBefore;
import lyh.library.cm.adapter.recycleview.MultiItemTypeAdapter;
import lyh.library.cm.adapter.recycleview.base.ItemViewDelegate;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;

/**
 * Created by lyh on 2016/12/30.
 */

public class ZhihuBeforeAdapter extends MultiItemTypeAdapter<ZhiHuBefore.StoriesBean> {

    public ZhihuBeforeAdapter(Context context, List<ZhiHuBefore.StoriesBean> datas) {
        super(context, datas);
        addItemViewDelegate(new Item_Time());
        addItemViewDelegate(new Item_Content());
    }

    /**
     * 标题，日期
     */
    public class Item_Time implements ItemViewDelegate<ZhiHuBefore.StoriesBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_zhihu_time;
        }

        @Override
        public boolean isForViewType(ZhiHuBefore.StoriesBean item, int position) {
            return false;
        }

        @Override
        public void convert(ViewHolder holder, ZhiHuBefore.StoriesBean date, int position) {
//            holder.setText(R.id.tv_zhihu_time, date);
        }
    }

    /**
     * 内容
     */
    public class Item_Content implements ItemViewDelegate<ZhiHuBefore.StoriesBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_zhihu_before_content;
        }

        @Override
        public boolean isForViewType(ZhiHuBefore.StoriesBean item, int position) {
            return false;
        }

        @Override
        public void convert(ViewHolder holder, ZhiHuBefore.StoriesBean storiesBean, int position) {
            holder.setText(R.id.tv_zhihu_before_content, storiesBean.getTitle())
                    .setImageViewUrl(holder.getContext(), R.id.iv_zhihu_before_content,
                            storiesBean.getImages().get(0));
        }
    }


}
