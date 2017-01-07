package core.framework.dev.data.http.api;

import core.framework.dev.bean.Feedback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lyh on 2016/11/19.
 * 视频评论相关接口
 * http://api.bilibili.cn/feedback
 * <p>
 * http://api.bilibili.cn/feedback/post
 * <p>
 * api文档
 * https://github.com/fython/BilibiliAPIDocs/blob/master/API.feedback.md
 */
public interface FeedbackService {

    /**
     * aid	true	int	AV号
     * page	true	int	页码
     * pagesize	false	int	单页返回的记录条数，最大不超过300，默认为10。
     * ver	false	int	API版本,最新是3
     * order	false	string	排序方式 默认按发布时间倒序 可选：good 按点赞人数排序 hot 按热门回复排序
     *
     * @param aid
     * @return
     */
    @GET("feedback")
    Observable<Feedback> getFeedback(
            @Query("aid") int aid,
            @Query("page") int page,
            @Query("pagesize") int pagesize,
            @Query("ver") int ver
    );

    /**
     * http://api.bilibili.cn/feedback/post
     *
     * @return
     */
    @POST("publishfeedback")
    Observable<Feedback> postFeedback(

    );

}
