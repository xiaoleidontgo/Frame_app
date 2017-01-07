package core.framework.dev.data.http;

import core.framework.dev.data.http.api.HomeRecommedService;
import core.framework.dev.utils.ZhiHuDateUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyh on 2016/11/22.
 * 程序网络请求方法的同一管理类
 */
public class HttpManager {

    private static HttpManager mHttpManager;

    private HttpManager() {

    }

    //not use
    public static HttpManager getInstance() {

        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                if (mHttpManager == null)
                    mHttpManager = new HttpManager();
            }
        }

        return mHttpManager;
    }

    /**______________________ZhiHu_____________________________*/

    /**
     * 获取知乎过往消息
     *
     * @param page
     * @param subscriber
     */
    public static void getZhiHuBefore(int page, Subscriber subscriber) {
        RetrofitHelper.getZhiHuApi()
                .getNewsOfDay(ZhiHuDateUtils.getCurrnetDate(page))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getZhiHuThemes(Subscriber subscriber) {
        RetrofitHelper.getZhiHuApi()
                .getZhihuThemes()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getZhiHuThemeContent(int id, Subscriber subscriber) {
        RetrofitHelper.getZhiHuApi()
                .getZhihuThemeContent(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public static void getZhiHuLatest(Subscriber subscriber) {
        RetrofitHelper.getZhiHuApi()
                .getZhihuLatest()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**______________________BiliBili_____________________________*/

    /**
     * Bilibili直播数据列表
     *
     * @param subscriber
     */
    public static void getBilibiliLive(Subscriber subscriber) {
        RetrofitHelper.getLiveInfoApi()
                .getLiveInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 合并首页推荐轮播，内容两个接口条目数据
     *
     * @param subscriber
     */
    public static void getBilibiliHomeRecommedInfo(Subscriber subscriber) {

        HomeRecommedService recommedApi = RetrofitHelper.getHomeRecommedApi();
        Observable
                .merge(
                        recommedApi.getRecommendedInfo(),
                        recommedApi.getRecommendedBannerInfo())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
