package core.framework.dev.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import core.framework.dev.R;
import core.framework.dev.config.Constant;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 闪屏页
 */
public class SplashActivity extends Activity {

    public static void launch(Activity from) {
        from.startActivity(new Intent(from, SplashActivity.class));
        from.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //延时2秒，结束进行逻辑跳转
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(aLong -> {
                    //如果登录则去主界面，未登录则去登录界面
                    boolean hasLogin = true;
                    if (hasLogin) {
                        return Observable.just(Constant.GO_MAIN);
                    }
                    return Observable.just(Constant.GO_LOGIN);
                })
                .subscribe(s -> {
                    if (s.equals(Constant.GO_MAIN)) {
                        gotoMainPage();
                    } else if (s.equals(Constant.GO_LOGIN)) {
                        gotoLoginPage();
                    }
                });
    }

    //未登录则去登录界面
    private void gotoLoginPage() {
        LoginActivity.launch(this);
    }

    //登陆了则去主界面
    private void gotoMainPage() {
        MainActivity.launch(this);
    }


}
