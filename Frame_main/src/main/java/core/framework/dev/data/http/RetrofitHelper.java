package core.framework.dev.data.http;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import core.framework.dev.FrameApp;
import core.framework.dev.data.http.api.FeedbackService;
import core.framework.dev.data.http.api.LiveInfoService;
import core.framework.dev.data.http.api.HomeRecommedService;
import core.framework.dev.data.http.api.ZhiHuService;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit帮助类
 */
public class RetrofitHelper {

    private static final String ZHIHU_BASE = "http://news-at.zhihu.com/api/4/";

    private static final String API_BASE_URL = "http://bilibili-service.daoapp.io/";

    private static final String MAIN_BASE_URL = "http://www.bilibili.com/";

    private static final String APP_BASE_URL = "http://app.bilibili.com/";

    private static final String LIVE_BASE_URL = "http://live.bilibili.com/";

    private static final String HOST_API_BASE_URL = "http://api.bilibili.cn/";

    public static final String HDSLB_HOST = "http://i2.hdslb.com";

    public static final String COMMON_UA_STR = "OhMyBiliBili Android Client/2.1 (100332338@qq.com)";

    /**
     * OkHttp 实例
     */
    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }

    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     * 设置UA拦截器
     */
    private static void initOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    Cache cache = new Cache(new File(FrameApp.getContext()
                            .getCacheDir(), "HttpCache"), 1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
//                            .addNetworkInterceptor(new StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())
                            .build();
                }
            }
        }
    }

    /**
     * 获取哔哩哔哩直播Api
     *
     * @return
     */
    public static LiveInfoService getLiveInfoApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LIVE_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(LiveInfoService.class);
    }

    /**
     * 获取视频评论
     *
     * @return
     */
    public static FeedbackService getFeedbackApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LIVE_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(FeedbackService.class);

    }

    /**
     * 获取首页推荐页数据
     *
     * @return
     */
    public static HomeRecommedService getHomeRecommedApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(HomeRecommedService.class);
    }


    //*************************知乎列表消息*********************************//
    /**
     * 知乎api相关
     */
    //启动界面图像
    String ZHIHU_IMAGE = "start-image/1080*1776";
    //版本检查
    String ZHIHU_VERSION = "version/android/2.3.0";
    //最新消息
    String NEWS_LATEST = "news/latest";
    //消息内容
    String ZHIHU_CONTENT = "news/";
    //过往消息
    String ZHIHU_BEFORE = "news/before/";
    //闪屏页图片
    String URL_IMGGE_START = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

    /**
     * 获取知乎列表，过往消息列表数据
     *
     * @return
     */
    public static ZhiHuService getZhiHuApi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ZHIHU_BASE)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ZhiHuService.class);

    }

    /**
     * 添加UA拦截器
     * B站请求API文档需要加上UA
     */
    static class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", COMMON_UA_STR)
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }
}
