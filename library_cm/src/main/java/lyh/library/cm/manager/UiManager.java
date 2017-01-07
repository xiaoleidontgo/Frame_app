package lyh.library.cm.manager;

/**
 * Created by lyh on 2016/11/10.
 * 界面管理，外界操作ui时，一些常用的入口
 */

public class UiManager {

    private static UiManager mUiManager;

    private UiManager() {
    }


    public static UiManager getInstance() {
        if (mUiManager == null) {
            synchronized (DataManager.class) {
                if (mUiManager == null)
                    mUiManager = new UiManager();
            }
        }
        return mUiManager;
    }

    /**
     * showToast
     */

    /**
     * showDialog
     */

    /**
     * ...and so on
     */

}
