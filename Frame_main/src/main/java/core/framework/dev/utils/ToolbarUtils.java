package core.framework.dev.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import core.framework.dev.R;
import lyh.library.cm.utils.LocalDisplay;

/**
 * Created by lyh on 2016/11/29.
 */

public class ToolbarUtils {

    private static int mDistanceY;

    public static void setToolbarGradient(final Toolbar toolbar, RecyclerView recyclerVie, final int gradientHeight) {

        if (toolbar == null || recyclerVie == null) return;
        final Drawable background = toolbar.getBackground();
        recyclerVie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDistanceY += dy;
                int toolbarHeight = toolbar.getBottom();
                if (mDistanceY <= toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    background.setAlpha((int) alpha);
                } else {
                    background.setAlpha(255);
                }
            }
        });


    }


}
