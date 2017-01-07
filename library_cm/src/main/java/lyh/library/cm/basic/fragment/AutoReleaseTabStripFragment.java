package lyh.library.cm.basic.fragment;

/**
 * Created by lyh on 2016/10/25.
 * 当页面资源耗费的内存过多时，进行自动资源释放的实现类
 */

public abstract class AutoReleaseTabStripFragment extends TabStripListFragment {

    @Override
    protected void onInvisible() {
        super.onInvisible();
    }

    @Override
    protected void onVisible() {
        super.onVisible();
    }
}
