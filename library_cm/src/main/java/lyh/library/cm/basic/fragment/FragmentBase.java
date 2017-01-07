package lyh.library.cm.basic.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lyh.library.cm.cube.CubeFragment;
import lyh.library.cm.utils.ToastUtils;
import lyh.library.cm.component.dialog.DialogManager;

/**
 * Created by lyh on 2016/10/21.
 * Fragment ui基类
 */

public abstract class FragmentBase extends CubeFragment {

    public static String TAG;

    /**
     * 当前Fragment的内容视图
     */
    private View mRootView;
    /**
     * 当前Fragment依附的Activity实例
     */
    protected Activity mActivity;

    /**
     * 标志当前Fragment的内容视图初始化完成
     */
    protected boolean isViewCreated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TAG = getClass().getSimpleName();

        if (getLayoutResId() > 0) {
            mRootView = inflater.inflate(getLayoutResId(), null);
            mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            initViews(mRootView);
            isViewCreated = true;
            return mRootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mActivity = getActivity();
            initData(mRootView);
            requestData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 页面内容id
     * <p>
     * 注意：此方法是子类都需要实现的，子某些特定的子类中
     * 由于界面行为不需要去复写这个方法，所以做了默认的实现（RecyclerView 等列表控件）
     * 如果你需要做一些自定义的界面效果，那么你需要去复写这个方法，
     * 但是，需要注意的是，复写之后的控件需要包含父类中的控件id,如果不要不想要父类中的
     * 一些控件，那么你需要去复写initViews();initData(),等父类的实现。
     * 建议：
     * 如果复写太过复杂可以选择不继承对应的父类，参考父类写对应的实现即可
     */
    protected abstract int getLayoutResId();

    /**
     * 返回Fragment的内容视图
     *
     * @return
     */
    protected View getRootView() {
        return mRootView;
    }

    /**
     * Fragment创建时会调用一次
     */
    public void requestData() {

    }

    /**
     * 当界面view创建好之后的回调
     * 注意：这个方法回调时，mActivity实例为空
     * 如果需要mActivity 可以移动到initData(View rootView)
     *
     * @param rootView
     */
    protected void initViews(View rootView) {

    }

    /**
     * 可以进行数据初始化的回调
     *
     * @param rootView
     */
    protected void initData(View rootView) {

    }

    /**
     * toast显示
     */
    public void showToast(int toastId) {
        ToastUtils.showShort(toastId);
    }

    public void showToast(String toastStr) {
        ToastUtils.showShort(toastStr);
    }

    /**
     * 进度框显示
     */
    protected void showProgressBar(String message) {
        DialogManager.createProgressDialog(mActivity, message, Color.parseColor("#7C7C7C")).show();
    }

    protected void showProgressBar(int messageId) {
        showProgressBar(getString(messageId));
    }

    /**
     * 进度框隐藏
     */
    protected void hideProgressBar() {
        DialogManager.dismissProgressDialog();
    }

    protected void setViewVisible(View view, int visible) {
        view.setVisibility(visible);
    }

    /**
     * findViewById
     */
    protected <T> T $(int id) {
        if (mRootView != null && id != 0) {
            T viewById = (T) mRootView.findViewById(id);
            return viewById;
        }
        throw new NullPointerException("can't find view by this id ->" + id);
    }

}
