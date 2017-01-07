package lyh.library.cm.widget.animLayout;

/**
 * Created by lyh on 2016/12/7.
 */
public interface ITargetView {

    /**
     * 准备开启
     */
    void onStartOpen();

    /**
     * 准备关闭
     */
    void onStartClose();

    /**
     * 当IMenu 达到了TargetView的位置
     */
    void onReached();


}