package core.framework.dev.data.http.api;

/**
 * Created by lyh on 2016/11/21.
 */

public interface NewsService {
    //新闻列表
    String URL_NEWSLIST = "http://api.1-blog.com/biz/bizserver/news/list.do?";
    //笑话列表
    String URL_JOKELIST = "http://api.1-blog.com/biz/bizserver/xiaohua/list.do?";
    //笑话图片列表
    String URL_JOKE_IMAGE = "http://japi.juhe.cn/joke/img/text.from?key=18a0431c1c5e25668e9b95af9545ae5f&";
    //热门新闻
    String URL_HOT_NEWSLIST = "http://op.juhe.cn/onebox/news/words?key=c460dfc39b3d3b0fa234dfb62d423526";

}
