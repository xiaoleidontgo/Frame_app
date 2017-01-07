package lyh.library.cm.adapter.expandable;

/**
 * 父条目对应实体
 *
 * @param <T>
 */
public class Group<T> {

    /**
     * 父条目名称
     */
    private final String name;

    /**
     * 是否打开/折叠
     */
    public boolean isExpanded;

    /**
     * 对应自定义数据格式
     */
    public T groupData;

    public Group(String name) {
        this.name = name;
        isExpanded = false;
    }

    public String getName() {
        return name;
    }
}
