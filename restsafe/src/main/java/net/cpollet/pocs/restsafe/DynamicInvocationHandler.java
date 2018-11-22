package net.cpollet.pocs.restsafe;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {
    private final Object target;

    public DynamicInvocationHandler(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(String.format("%s was called", method.getName()));
        try {
            return method.invoke(target, args);
        } catch (Exception e) {
            System.out.println(String.format("Got exception %s, returning 0", e.getClass().getName()));
            return 0;
        }
    }
}
