package core.framework.dev.ui.video;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import core.framework.dev.R;
import lyh.library.cm.basic.activity.ActivityBase;

/**
 * 推流直播间
 */
public class PushVideoActivity extends ActivityBase {

    public static void launch(Activity from) {
        Intent intent = new Intent(from, PushVideoActivity.class);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.in_from_bottom, R.anim.no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_video);
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
