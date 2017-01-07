package core.framework.dev.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import core.framework.dev.R;
import core.framework.dev.base.BaseActivity;
import core.framework.dev.databinding.ActivityLoginBinding;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity {

    public static void launch(Activity from) {
        from.startActivity(new Intent(from, LoginActivity.class));
        from.overridePendingTransition(R.anim.in_from_bottom, R.anim.no_anim);
    }

    ActivityLoginBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initViews();
        initData();
        mBinding.viewSwitcher.setDisplayedChild(0);
        mBinding.btnSwap.setOnClickListener(v -> {
            if (mBinding.tvOne.getVisibility() == View.VISIBLE) {
                mBinding.viewSwitcher.setInAnimation(LoginActivity.this, R.anim.in_from_bottom);//right
                mBinding.viewSwitcher.setOutAnimation(LoginActivity.this, R.anim.out_from_bottom);
                mBinding.viewSwitcher.showNext();

            } else {
                mBinding.viewSwitcher.setInAnimation(LoginActivity.this, R.anim.in_from_center);
                mBinding.viewSwitcher.setOutAnimation(LoginActivity.this, R.anim.out_from_top);

                mBinding.viewSwitcher.showPrevious();

            }
        });
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    private void exitWithAnim() {
        finish();
        overridePendingTransition(R.anim.no_anim, R.anim.out_from_top);
    }

    @Override
    protected boolean processBackPressed() {
        exitWithAnim();
        return true;
    }
}
