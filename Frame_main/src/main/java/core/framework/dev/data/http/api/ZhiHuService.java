package core.framework.dev.data.http.api;

import core.framework.dev.bean.ZhiHuBefore;
import core.framework.dev.bean.ZhihuLatest;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lyh on 2016/11/21.
 */

public interface ZhiHuService {

    /**
     * 消息内容
     */
    String ZHIHU_CONTENT = "http://news-at.zhihu.com/api/4/news/";
    String ZHIHU_WEB_URL = "http://daily.zhihu.com/story/";

    /**
     * 知乎消息
     *
     * @param before 日期
     * @return
     */
    @GET("news/before/{before}")
    Observable<ZhiHuBefore> getNewsOfDay(@Path("before") String before);

    @GET("news/latest")
    Observable<ZhihuLatest> getZhihuLatest();

    @GET("news/themes")
    Observable<ZhiHuBefore> getZhihuThemes();

    @GET("news/theme/id/{id}")
    Observable<ZhiHuBefore> getZhihuThemeContent(int id);

}
