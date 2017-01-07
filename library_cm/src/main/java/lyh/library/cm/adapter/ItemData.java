package lyh.library.cm.adapter;

/**
 * Created by lyh on 2017/1/4.
 * 封装的实体数据类型
 */

public final class ItemData<T> {

    /**
     * 必须包含一个真实数据
     *
     * @param data
     */
    public ItemData(T data) {
        this.data = data;
    }

    public T data;

}