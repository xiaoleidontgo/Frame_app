package lyh.library.cm.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 */
public class ToastUtils {

    private ToastUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 管理App是否弹出吐司
     */
    public static boolean isShow;
    public static Context mContext;

    public static void register(Context context, boolean debug) {
        isShow = debug;
        mContext = context.getApplicationContext();
    }

    private static void isNullable() {
        if (mContext == null)
            throw new NullPointerException("the context is null , do you " +
                    "register this ToastUtils before you use?");
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow) {
            isNullable();
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(int message) {
        if (isShow) {
            isNullable();
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        if (isShow) {
            isNullable();
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        if (isShow) {
            isNullable();
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(CharSequence message, int duration) {
        if (isShow) {
            isNullable();
            Toast.makeText(mContext, message, duration).show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(int message, int duration) {
        if (isShow) {
            isNullable();
            Toast.makeText(mContext, message, duration).show();
        }
    }

}