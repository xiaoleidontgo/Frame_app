package core.framework.dev.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import core.framework.dev.R;
import core.framework.dev.base.BaseActivity;
import core.framework.dev.databinding.ActivityWebBinding;

/**
 * 网络链接
 */
public class WebActivity extends BaseActivity {

    ActivityWebBinding mBinding;
    private String openUrl = "";

    public static void launch(Activity from, String openUrl) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra("open_url", openUrl);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);

        Intent intent = getIntent();
        if (intent == null) finish();
        openUrl = intent.getStringExtra("open_url");
        initViews();
        initData();

    }

    @Override
    public void initData() {
        super.initData();
        mBinding.webView.loadUrl(openUrl);
        //启用支持javascript
        WebSettings settings = mBinding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mBinding.progressBar.setVisibility(View.GONE);
                } else {
                    mBinding.progressBar.setProgress(newProgress);
                }

            }
        });


    }

    @Override
    protected boolean processBackPressed() {

        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
            return true;
        }
        return super.processBackPressed();
    }
}
