package lyh.library.cm.basic;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import lyh.library.cm.utils.LocalDisplay;
import lyh.library.cm.utils.ToastUtils;

/**
 * Created by lyh on 2016/11/24.
 */
public class _Application extends Application {

    private static _Application _Context;
    public static boolean DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();
        _Context = this;
        setDebug(true);
        ToastUtils.register(_Context, DEBUG);
        LocalDisplay.init(this);
    }

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static _Application get_Context() {
        return _Context;
    }

    public static _Application getInstance() {
        return get_Context();
    }

    public Handler getHandler() {
        return mHandler;
    }

    Handler mHandler = new Handler() {

    };

    /**
     * 程序文件路径
     *
     * @return
     */
    public String getAppPath() {
        return "";
    }

    /**
     * 获取应用程序的缓存路径
     *
     * @return
     */
    public String getCachePath() {
        return getAppPath() + "/cache";
    }

    /**
     * 获取图片缓存路径
     *
     * @return
     */
    public String getImageCachePath() {
        return getCachePath() + "/img";
    }

}
