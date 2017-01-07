package lyh.library.cm.widget.animLayout;

/**
 * Created by lyh on 2016/12/7.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lyh.library.cm.component.tabbar.TabBarLayout;
import lyh.library.ultra.R;

/**
 * 旋转的控制View的实现
 */
public class RotateTargetView extends LinearLayout implements ITargetView {

    private ImageView imageView;
    private boolean isOpen = false;

    public RotateTargetView(Context context) {
        this(context, null);
    }

    public RotateTargetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateTargetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init
        LayoutInflater.from(context).inflate(R.layout.layout_tab_rotate, this);
        imageView = (ImageView) findViewById(R.id.iv_icon_add);
    }

    @Override
    public void onStartOpen() {
        toggleMenu();
    }


    @Override
    public void onStartClose() {
        toggleMenu();
    }

    private void toggleMenu() {
        if (!isOpen) {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_open_anim);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        } else {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_close_anim);
            animation.setFillAfter(true);
            imageView.startAnimation(animation);
        }
        isOpen = !isOpen;
    }

    @Override
    public void onReached() {

    }

}
