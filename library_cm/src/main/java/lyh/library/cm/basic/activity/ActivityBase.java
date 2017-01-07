package lyh.library.cm.basic.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import lyh.library.cm.cube.XActivity;
import lyh.library.cm.utils.StatusBarUtils;
import lyh.library.cm.utils.SystemBarHelper;
import lyh.library.cm.utils.ToastUtils;
import lyh.library.cm.component.dialog.DialogManager;
import lyh.library.ultra.R;


/**
 * Created by lyh on 2016/10/21.
 * Library__ActivityBase
 * --沉浸栏
 * --内部Fragment管理
 */

public class ActivityBase extends XActivity {

    public static String TAG;

    private View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();

    }

    public void setStatusBarColor(int color) {
        SystemBarHelper.tintStatusBar(this, color);
    }

    public void setStatusBarColorID(int colorId) {
        SystemBarHelper.tintStatusBar(this, getResources().getColor(colorId));
    }

    public void immersiveSystemBar() {
        SystemBarHelper.immersiveStatusBar(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    /**
     * 适当时候调用此方法
     */
    public void initViews() {

    }

    /**
     * 适当时候调用此方法
     */
    public void initData() {
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRootView = view;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        mRootView = view;
    }


    /**
     * 在setContentView设置成功之后
     * not use
     */
    protected void onContentViewSetFinished() {
        //布局设置好之后的初始化动作
        initViews();
        initData();
    }


    /**
     * layout视图根节点
     *
     * @return
     */
    protected View getRootView() {
        return mRootView;
    }

    /**
     * 进度框显示
     */
    protected void showProgressBar(String message) {
        DialogManager.createProgressDialog(ActivityBase.this, message, Color.parseColor("#7C7C7C")).show();
    }

    /**
     * 进度框显示
     */
    protected void showProgressBar(int messageId) {
        showProgressBar(getString(messageId));
    }

    /**
     * 进度框隐藏
     */
    protected void hideProgressBar() {
        DialogManager.dismissProgressDialog();
    }

    /**
     * 吐司展示
     *
     * @param toastId
     */
    public void showToast(int toastId) {
        ToastUtils.showShort(toastId);
    }

    /**
     * 吐司展示
     */
    public void showToast(String toastStr) {
        ToastUtils.showShort(toastStr);
    }

    //键盘相关
    public void hideKeyboardForCurrentFocus() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void showKeyboardAtView(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public void forceShowKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    /**
     * Activity全屏
     */
    protected void exitFullScreen() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    /**
     * 加载内部Fragment容器id
     */
    @Override
    protected int getFragmentContainerId() {
        return R.id.frame_container;
    }

    /**
     * 关闭此Activity 弹出的警告
     *
     * @return
     */
    @Override
    protected String getCloseWarning() {
        return super.getCloseWarning();
    }

    /**
     * findViewById
     * mvvm 后使用-> XXAtivityBinding
     */
    protected <T> T $(int id) {
        if (mRootView != null && id != 0) {
            T viewById = (T) findViewById(id);
            return viewById;
        }
        throw new NullPointerException("can't find view by this id ->" + id);
    }
}
