package lyh.library.cm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 模板模式
 * Created by lyh on 2016/7/14.
 * ViewPager适配器的一个模板，
 * 封装了不变部分，抽象出改变部分让子类实现
 * 行为父类控制，子类实现。子类可以通过扩展的方法增加相应的功能
 * 符合开闭原则
 */
public abstract class BasePagerAdapter extends PagerAdapter {

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.getView(LayoutInflater.from(container.getContext()), position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public abstract View getView(LayoutInflater inflater, int position);
}
