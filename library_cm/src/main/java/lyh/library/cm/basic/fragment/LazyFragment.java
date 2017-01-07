package lyh.library.cm.basic.fragment;


/**
 * Created by lyh on 2016/10/25.
 * 懒加载
 * beforeLazyRequest  会在Fragment第一次创建时调用，  lazyRequest 会在Fragment可见时调用
 */

public abstract class LazyFragment extends FragmentBase {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    /**
     * 表示懒加载是否已经进行过
     */
    protected boolean isLazyLoadFirst = true;

    /**
     * 当Fragment可见性改变时会调用
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * Fragment可见时会调用可见
     */
    protected void onVisible() {
        if (isViewCreated && isLazyLoadFirst) {
            lazyRequest();
            isLazyLoadFirst = false;
        }
    }


    /**
     * Fragment不可见时会调用
     */
    protected void onInvisible() {
    }

    /**
     * 依附的Activity创建好即调用此方法
     */
    @Override
    public void requestData() {
        super.requestData();
        beforeLazyRequest();
    }

    /**
     * 会在Fragment第一次创建时调用（view刚创建好）,可在此加载缓存数据，
     * 或者做一些空试图的初始化等操作
     */
    public abstract void beforeLazyRequest();

    /**
     * 子类加载数据，正常请求网络数据
     * <p>
     * 一般来讲由于setUserVisibleHint 在View创建之前就会调用。
     * 所以为了防止空指针，这里做了一些处理：当Fragment默认为界面一加载就会显示的Fragment
     * 则不会调用此方法，但会调用beforeLazyRequest()
     */
    public abstract void lazyRequest();

    /**
     * Fragment的View被销毁会调用此方法，为了切换回来能够再次回调LazyRequest()
     * 将isLazyLoadFirst归位
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLazyLoadFirst = true;
    }

}
