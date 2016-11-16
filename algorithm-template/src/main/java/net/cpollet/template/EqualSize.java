package net.cpollet.template;

import java.util.List;

/**
 * Created by cpollet on 16.11.16.
 */
public class EqualSize<T> implements Work<T, EqualSizeWorkload<T>> {
    @Override
    public boolean work(EqualSizeWorkload<T> workload) {
        return workload.getList1().size()==workload.getList2().size();
    }

    @Override
    public List<T> things(EqualSizeWorkload<T> workload) {
        return workload.getList1();
    }
}
