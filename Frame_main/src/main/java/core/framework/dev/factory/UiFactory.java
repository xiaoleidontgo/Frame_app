package core.framework.dev.factory;

import android.content.Context;
import android.graphics.drawable.Drawable;

import core.framework.dev.R;
import lyh.library.cm.component.tabbar.TabBarLayout;
import lyh.library.cm.component.tabbar.view.ClassicTab;

/**
 * Created by Administrator on 2016/10/31.
 * 加载界面一些数据
 */

public class UiFactory {

    public static void buildTab(TabBarLayout tabBarLayout, Context context) {
        //标题
        String[] tabbars = context.getResources().getStringArray(R.array.array_main_tabbar);

        //颜色
        int normalColor = context.getColor(R.color.colorGray);
        int pressedColor = context.getColor(R.color.colorPrimary);

        //tabbar 图标
        Drawable homeNormal = context.getResources().getDrawable(R.mipmap.home_normal);
        Drawable homePressed = context.getResources().getDrawable(R.mipmap.home_pressed);

        tabBarLayout.addTab(new ClassicTab(context, tabbars[0], homeNormal, homePressed,
                normalColor, pressedColor));

        Drawable imNormal = context.getResources().getDrawable(R.mipmap.im_normal);
        Drawable imPressed = context.getResources().getDrawable(R.mipmap.im_pressed);

        tabBarLayout.addTab(new ClassicTab(context, tabbars[1], imNormal, imPressed,
                normalColor, pressedColor));

        Drawable findNormal = context.getResources().getDrawable(R.mipmap.find_normal);
        Drawable findPressed = context.getResources().getDrawable(R.mipmap.find_pressed);

        tabBarLayout.addTab(new ClassicTab(context, tabbars[2], findNormal, findPressed,
                normalColor, pressedColor));

        Drawable photoNormal = context.getResources().getDrawable(R.mipmap.photo_normal);
        Drawable photoPressed = context.getResources().getDrawable(R.mipmap.photo_pressed);

        tabBarLayout.addTab(new ClassicTab(context, tabbars[3], photoNormal, photoPressed,
                normalColor, pressedColor));

        Drawable meNormal = context.getResources().getDrawable(R.mipmap.me_normal);
        Drawable mePressed = context.getResources().getDrawable(R.mipmap.me_pressed);

        tabBarLayout.addTab(new ClassicTab(context, tabbars[4], meNormal, mePressed,
                normalColor, pressedColor));
    }
}
