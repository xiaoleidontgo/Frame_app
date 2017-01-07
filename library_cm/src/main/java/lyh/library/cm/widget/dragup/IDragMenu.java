package lyh.library.cm.widget.dragup;

/**
 * Created by lyh on 2016/11/21.
 */

public interface IDragMenu {

    /**
     * 菜单内容是否可以进行上拉操作
     *
     * @return
     */
    boolean canDragUp();

    /**
     * 默认展示的菜单内容高度
     *
     * @return
     */
    int showMenuHeight();


}
