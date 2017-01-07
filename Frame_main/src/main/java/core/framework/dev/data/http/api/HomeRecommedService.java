package core.framework.dev.data.http.api;

import core.framework.dev.bean.RecommedBannerInfo;
import core.framework.dev.bean.RecommedInfo;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lyh on 2016/12/11.
 */
public interface HomeRecommedService {

    @GET("x/show/old?platform=android&device=&build=412001")
    Observable<RecommedInfo> getRecommendedInfo();

    @GET("x/banner?plat=4&build=411007&channel=bilih5")
    Observable<RecommedBannerInfo> getRecommendedBannerInfo();

}
