package net.cpollet.template;

import java.util.List;

/**
 * Created by cpollet on 16.11.16.
 */
public class EqualSizeWorkload<T> implements Workload {
    private final List<T> list1;
    private final List<T> list2;

    public EqualSizeWorkload(List<T> list1, List<T> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    public List<T> getList1() {
        return list1;
    }

    public List<T> getList2() {
        return list2;
    }
}
