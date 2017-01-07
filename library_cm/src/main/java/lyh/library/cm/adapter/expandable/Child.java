package lyh.library.cm.adapter.expandable;

/**
 * 子条目对应实体
 *
 * @param <T>
 */
public class Child<T> {

    /**
     * 子条目名称
     */
    private final String name;
    /**
     * 子条目id
     */
    private int id;

    /**
     * 子条目自定义数据格式
     */
    public T itemData;

    public Child(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Child(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
