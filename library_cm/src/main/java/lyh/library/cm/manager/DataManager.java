package lyh.library.cm.manager;

/**
 * Created by lyh on 2016/11/10.
 * 数据统一管理，外界获取数据统一通过此类获取
 */

public class DataManager {

    private static DataManager mDataManager;

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (mDataManager == null) {
            synchronized (DataManager.class) {
                if (mDataManager == null)
                    mDataManager = new DataManager();
            }
        }
        return mDataManager;
    }


    /**
     * 根据键获取保存的缓存数据
     *
     * @param key
     * @return
     */
    public String getCache(String key) {

        return null;
    }

    /**
     * 保存缓存数据
     *
     * @param key
     * @param cacheContent
     */
    public void putCache(String key, String cacheContent) {

    }

    /**
     * 将一个对象数据存到数据库
     *
     * @param tableName
     * @param obj
     */
    public void insertDB(String tableName, Object obj) {

    }

    /**
     * 从数据库里查出一条数据
     *
     * @param tableName
     * @param key
     */
    public void queryDBz(String tableName, String key) {

    }


}
