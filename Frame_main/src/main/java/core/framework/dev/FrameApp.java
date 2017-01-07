package core.framework.dev;

import lyh.library.cm.basic._Application;

/**
 * Created by lyh on 2016/10/21.
 */

public class FrameApp extends _Application {

    private static FrameApp mFrameApp;

    @Override
    public void onCreate() {
        super.onCreate();
        setDebug(true);
        mFrameApp = this;
    }

    public static FrameApp getContext() {
        return mFrameApp;
    }

}
