package core.framework.dev.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import core.framework.dev.R;
import core.framework.dev.ui.video.PushVideoActivity;
import lyh.library.cm.basic.activity.ActivityBase;

/**
 * 卡片-图片
 */
public class PhotoCardActivity extends ActivityBase {

    public static void launch(Activity from) {
        Intent intent = new Intent(from, PhotoCardActivity.class);
        from.startActivity(intent);
        from.overridePendingTransition(R.anim.in_from_bottom, R.anim.no_anim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_card);

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
