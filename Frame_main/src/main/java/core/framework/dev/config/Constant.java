package core.framework.dev.config;

/**
 * Created by lyh on 2016/10/23.
 * 常量类
 */

public interface Constant {

    /**
     * 是否展示过app介绍页
     */
    String PAGE_WELCOME_SHOWED = "page_welcome_showed";

    /**
     * where to go?
     */
    String GO_MAIN = "go_main";

    String GO_LOGIN = "go_login";

    /**
     * 当前登录的用户
     */
    String CURRENT_USER = "current_user";

    /**
     * 标识当前是否已经登录
     */
    String IS_LOGIN = "isLogin";

    /**
     * 无效代码
     */
    int INVALID_CODE = -1;

}
