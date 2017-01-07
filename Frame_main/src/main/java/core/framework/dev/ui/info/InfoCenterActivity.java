package core.framework.dev.ui.info;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import core.framework.dev.R;
import lyh.library.cm.basic.activity.ActivityBase;

/**
 * 资讯中心
 */
public class InfoCenterActivity extends ActivityBase {

    public static void launch(Activity from) {
        Intent intent = new Intent(from, InfoCenterActivity.class);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.in_from_bottom, R.anim.no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_center);
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
