package lyh.library.cm.display;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import lyh.library.ultra.R;

/**
 * Created by lyh on 2016/11/11.
 */

public class BitmapLoader {

    /**
     * 加载位图
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadBmp(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.empty_big)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载位图为圆形
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadCircleBmp(Context context, ImageView imageView, String url) {
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.empty_big)
                .centerCrop()
                .into(imageView);
    }

}
