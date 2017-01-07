package core.framework.dev.ui.entry;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import core.framework.dev.R;
import core.framework.dev.base.BaseActivity;
import core.framework.dev.databinding.ActivityFollowAnchorBinding;

/**
 * 关注中心
 */
public class FollowAnchorActivity extends BaseActivity {

    ActivityFollowAnchorBinding mBinding;

    public static void launch(Activity from, String title) {
        Intent intent = new Intent(from, FollowAnchorActivity.class);
        intent.putExtra("title", title);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColorID(R.color.b_color_theme);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_follow_anchor);
        initViews();
        initData();
    }


    @Override
    public void initViews() {
        super.initViews();
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener(v -> finish());

    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        getSupportActionBar().setTitle(title);

    }
}
