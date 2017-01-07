package core.framework.dev.ui.channel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import core.framework.dev.R;
import lyh.library.cm.basic.fragment.LazyFragment;
import lyh.library.cm.widget.dropdown.DropDownLayout;

/**
 * Created by lyh on 2016/11/4.
 * 频道中心
 */
public class ChannelCenterFragment extends LazyFragment {

    public static ChannelCenterFragment newInstance() {
        Bundle args = new Bundle();
        ChannelCenterFragment fragment = new ChannelCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeLazyRequest() {

    }

    @Override
    public void lazyRequest() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_channel_center;
    }


    @Override
    protected void initViews(View rootView) {
        super.initViews(rootView);
    }

    @Override
    protected void initData(View rootView) {
        super.initData(rootView);
        Button btn1 = $(R.id.btn_filter1);
        Button btn2 = $(R.id.btn_filter2);
        Button btn3 = $(R.id.btn_filter3);

        final DropDownLayout dropDownLayout = $(R.id.filter_menu);
        List<View> pages = new ArrayList<>();
        pages.add(LayoutInflater.from(mActivity).inflate(R.layout.item_im_message, null));
        pages.add(LayoutInflater.from(mActivity).inflate(R.layout.item_contact_parent, null));
        dropDownLayout.setPages(pages);

        btn1.setOnClickListener(v -> btn1Click(dropDownLayout));

        btn2.setOnClickListener(v -> btn2Click(dropDownLayout));

        btn3.setOnClickListener(v -> dropDownLayout.closeAllPage());
    }

    private void btn1Click(DropDownLayout dropDownLayout) {
        if (dropDownLayout.hasOpenPage()) {
            int openPage = dropDownLayout.getOpenPageIndex();
            if (openPage != 0)
                dropDownLayout.closePageAt(openPage);
        }
        dropDownLayout.openPageAt(0);
    }

    private void btn2Click(DropDownLayout dropDownLayout) {
        if (dropDownLayout.hasOpenPage()) {
            int openPage = dropDownLayout.getOpenPageIndex();
            if (openPage != 1)
                dropDownLayout.closePageAt(openPage);
        }
        dropDownLayout.openPageAt(1);
    }


}
