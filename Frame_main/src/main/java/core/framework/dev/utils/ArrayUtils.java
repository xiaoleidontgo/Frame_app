package core.framework.dev.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/11/11.
 */

public class ArrayUtils {

    /**
     * 将一个集合按照规定的索引，分割成两个集合
     *
     * @param targetList
     * @param splitIndex
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitListByIndex(List<T> targetList, int splitIndex) {
        if (targetList == null) return null;

        List<List<T>> lists = new ArrayList<>();
        List<T> aList = new ArrayList<>();
        List<T> bList = new ArrayList<>();
        for (int i = 0; i < targetList.size(); i++) {
            if (i <= splitIndex) {
                aList.add(targetList.get(i));
            } else {
                bList.add(targetList.get(i));
            }
        }
        lists.add(aList);
        lists.add(bList);
        return lists;
    }


}
