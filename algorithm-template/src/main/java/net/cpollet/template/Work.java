package net.cpollet.template;

import java.util.List;

/**
 * Created by cpollet on 16.11.16.
 */
public interface Work<T, W extends Workload> {
    boolean work(W workload);

    List<T> things(W workload);
}
