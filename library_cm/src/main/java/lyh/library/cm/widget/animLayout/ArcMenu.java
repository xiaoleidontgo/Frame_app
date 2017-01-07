package lyh.library.cm.widget.animLayout;

/**
 * Created by lyh on 2016/12/7.
 */

import android.content.Context;
import android.graphics.Path;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import lyh.library.cm.utils.ToastUtils;

/**
 * 类似于ArcMenu的实现
 */
public class ArcMenu extends ImageView implements IMenu {

    /**
     * ArcMenu路径的起点与终点
     */
    float startX, startY, endX, endY;

    private AnimLayout animLayout;

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onInitMovePosition(AnimLayout animLayout, float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.animLayout = animLayout;
    }

    @Override
    public void startOpen() {
        ArcMenu.this.setVisibility(VISIBLE);
        AnimationSet animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        animationSet.addAnimation(alphaAnimation);
        TranslateAnimation animOpen =
                new TranslateAnimation(endX - startX, 0, endY - startY - getHeight() / 2, 0);
        animOpen.setFillAfter(true);
        animOpen.setDuration(300);
        animOpen.setInterpolator(new FastOutSlowInInterpolator());
        animationSet.addAnimation(animOpen);
        animationSet.setFillAfter(true);
        ArcMenu.this.startAnimation(animationSet);
    }

    @Override
    public void startClose() {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(getAlphaAnim());
        TranslateAnimation animClose =
                new TranslateAnimation(0, endX - startX, 0, endY - startY - getHeight() / 2);
        animClose.setFillAfter(true);
        animClose.setInterpolator(new FastOutLinearInInterpolator());
        animClose.setDuration(300);
        animationSet.addAnimation(animClose);
        animationSet.setFillAfter(true);
        ArcMenu.this.startAnimation(animationSet);

    }

    @Override
    public void onMenuClick(boolean isSelf) {
        if (isSelf) {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.addAnimation(getAlphaAnim());
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.5f, 1, 1.5f,
                    Animation.RELATIVE_TO_SELF, 1.5f, Animation.RELATIVE_TO_SELF, 1.5f);
            scaleAnimation.setDuration(300);
            animationSet.addAnimation(scaleAnimation);
            ArcMenu.this.startAnimation(animationSet);
        } else {
            startClose();
        }
        animLayout.setMenuStatus(false);
    }

    @Override
    public Path openPath() {
        return null;
    }

    @Override
    public Path closePath() {
        return null;
    }

    @Override
    public void onMenuMove(boolean status, int moveX, int moveY) {

    }

    @Override
    public void onMoveStop(AnimLayout.Status status) {
        ArcMenu.this.clearAnimation();
    }


    private AlphaAnimation getAlphaAnim() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuClosed();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        return alphaAnimation;
    }

    private void menuClosed() {
        ArcMenu.this.setVisibility(GONE);
        ArcMenu.this.clearAnimation();
        ArcMenu.this.invalidate();
    }


}