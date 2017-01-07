package core.framework.dev.data.http;

import core.framework.dev.data.http.api.FeedbackService;
import core.framework.dev.data.http.api.HomeRecommedService;
import core.framework.dev.data.http.api.NewsService;
import core.framework.dev.data.http.api.ZhiHuService;

/**
 * Created by lyh on 2016/12/16.
 */
public class ApiFactory {

    private NewsService newsService;
    private ZhiHuService zhiHuService;
    private HomeRecommedService homeRecommedService;
    private FeedbackService feedbackService;

    private ApiFactory() {
    }

}
