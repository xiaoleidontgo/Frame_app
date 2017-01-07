package core.framework.dev.ui.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import core.framework.dev.R;
import core.framework.dev.config.Constant;
import lyh.library.cm.utils.SPUtils;

/**
 * 欢迎介绍页
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean showed = true;

        if (showed) {
            gotoSplashPage();
            //添加标识，已经展示过一次这个界面
            SPUtils.put(this, Constant.PAGE_WELCOME_SHOWED, true);

        } else {
            setContentView(R.layout.activity_welcome);
        }

    }

    //跳转去闪屏页
    private void gotoSplashPage() {
        SplashActivity.launch(this);
    }


}
