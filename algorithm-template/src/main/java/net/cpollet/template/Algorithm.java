package net.cpollet.template;

import java.util.Collections;
import java.util.List;

/**
 * Created by cpollet on 16.11.16.
 */
public class Algorithm<T> {

    public static void main(String[] args) {
        Algorithm<String> algorithm = new Algorithm<>();

        System.out.println(algorithm.algorithm("name", "value")); // should be true
    }

    public boolean algorithm(T name, T value) {
        return algorithm(Collections.singletonList(name), Collections.singletonList(value));
    }

    public boolean algorithm(List<T> names, List<T> values) {
        return execute(new EqualSize<>(), new EqualSizeWorkload<>(names, values));
    }

    private <W extends Workload> boolean execute(Work<T, W> work, W workload) {
        part1(work.things(workload));

        boolean result = work.work(workload);

        return part2(result);
    }

    private boolean part2(boolean result) {
        return !result;
    }

    private void part1(List<T> things) {
        System.out.println(things.size());
    }
}
