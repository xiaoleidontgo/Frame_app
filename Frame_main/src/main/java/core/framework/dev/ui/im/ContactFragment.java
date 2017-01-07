package core.framework.dev.ui.im;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.framework.dev.R;
import core.framework.dev.adapter.ContactAdapter;
import lyh.library.cm.adapter.expandable.Child;
import lyh.library.cm.adapter.expandable.Group;
import lyh.library.cm.basic.fragment.SwipeRefreshListFragment;
import lyh.library.cm.widget.PinHeaderLayout;

/**
 * Created by lyh on 2016/10/31.
 * IM 下 联系人
 */

public class ContactFragment extends SwipeRefreshListFragment<Object> {

    public static ContactFragment newInstance() {
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private PinHeaderLayout pinHeaderLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
        pinHeaderLayout = $(R.id.pin_header);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);

    }


    @Override
    public RecyclerView.Adapter initAdapter(List<Object> data) {
        return new ContactAdapter(mActivity, getAdapterData(), 1, R.layout.layout_item, R.layout.item_im_message);
    }

    @Override
    public void lazyRequest() {

    }

    @Override
    public void initAdapterData(List<Object> adapterData) {
        Map<Group, ArrayList<Child>> maps = new HashMap<>();

        ArrayList<Child> list = new ArrayList();
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        maps.put(new Group("公司"), list);

        list = new ArrayList();
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        maps.put(new Group("上海"), list);

        list = new ArrayList();
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        list.add(new Child("item"));
        list.add(new Child("張三"));
        list.add(new Child("小二"));
        maps.put(new Group("科院"), list);

        ContactAdapter adapter = getAdapter();

        adapter.putGroupData(maps);
    }

}
