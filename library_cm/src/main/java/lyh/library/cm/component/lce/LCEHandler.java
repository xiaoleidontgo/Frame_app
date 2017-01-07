package lyh.library.cm.component.lce;


import android.view.View;

/**
 * Created by lyh on 2016/7/11.
 */
public interface LCEHandler {
    /**
     * 显示加载，空，无网络等视图
     */
    void showLoadLayout();

    /**
     * 显示内容视图
     */
    void showContentLayout();

    /**
     * 隐藏加载视图
     */
    void hideLCELayout();

    /**
     * 隐藏内容视图
     */
    void hideContentLayout();

    /**
     * 获取加载视图
     *
     * @return
     */
    LCEUiHandler getLCEUiView();

    /**
     * 获取内容视图
     *
     * @return
     */
    View getContentView();

}
