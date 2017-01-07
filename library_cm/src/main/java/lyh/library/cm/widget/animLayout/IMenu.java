package lyh.library.cm.widget.animLayout;

import android.graphics.Path;

/**
 * Created by lyh on 2016/12/7.
 */
public interface IMenu {

    /**
     * Menu的运动起始位置，结束位置
     */
    void onInitMovePosition(AnimLayout animLayout, float startX, float startY, float endX, float endY);

    /**
     * 开始打开
     */
    void startOpen();

    /**
     * 开始关闭
     */
    void startClose();

    /**
     * 被点击
     */
    void onMenuClick(boolean isSelf);

    /**
     * 根据容器提供过的起始，结束位置。自己决定自己的打开轨迹
     */
    Path openPath();

    /**
     * 关闭轨迹
     *
     * @return
     */
    Path closePath();

    /**
     * menu运动时的回调，menu可在此做一些自身特殊的处理，比如在某个位置动态隐藏
     */
    void onMenuMove(boolean status, int moveX, int moveY);

    /**
     * 运动到终点时回调
     */
    void onMoveStop(AnimLayout.Status status);
}
