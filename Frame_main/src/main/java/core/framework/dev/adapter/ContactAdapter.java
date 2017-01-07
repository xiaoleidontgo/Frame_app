package core.framework.dev.adapter;

import android.content.Context;

import java.util.List;

import core.framework.dev.R;
import lyh.library.cm.adapter.expandable.Child;
import lyh.library.cm.adapter.recycleview.base.ViewHolder;
import lyh.library.cm.adapter.expandable.Group;
import lyh.library.cm.adapter.expandable.ExpandableAdapter;

/**
 * Created by lyh on 2016/10/31.
 */

public class ContactAdapter extends ExpandableAdapter<Group<String>, Child<String>> {

    public ContactAdapter(Context context, List<Object> datas, int childSpan, int parentLayoutId, int chileLayoutId) {
        super(context, datas, childSpan, parentLayoutId, chileLayoutId);
    }

    @Override
    public void convertParent(ViewHolder holder, Group t, int position) {
        holder.setText(R.id.tv_text, t.getName());
    }

    @Override
    public void convertChild(ViewHolder holder, Child t, int position) {
        holder.setText(R.id.tv_name, t.getName());
    }

}
