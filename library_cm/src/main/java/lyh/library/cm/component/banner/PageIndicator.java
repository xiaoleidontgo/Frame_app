package lyh.library.cm.component.banner;

/**
 * Created by lyh on 2016/7/14.
 * 轮播图的指示控件
 */
public interface PageIndicator {

    enum MODE {
        LEFT, CENTER, RIGHT
    }

    void setIndicatorNum(int totalNum);

    void onIndicatorSelected(int current);

    void onIndicatorScroll(int position, float positionOffset, int positionOffsetPixels);

    void setIndicatorMode(MODE mode);


}
