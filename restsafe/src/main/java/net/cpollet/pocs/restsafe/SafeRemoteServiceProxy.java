package net.cpollet.pocs.restsafe;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SafeRemoteServiceProxy<T> {
    private final T target;
    private final Class<T> implementedInterface;

    public SafeRemoteServiceProxy(T target, Class<T> implementedInterface) {
        this.target = target;
        this.implementedInterface = implementedInterface;
    }

    @SuppressWarnings("unchecked")
    public T build(Callback onError) {
        if (onError == null) {
            throw new IllegalArgumentException("onError cannot be null");
        }

        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{implementedInterface},
                new SafeInvocationHandler(target, onError)
        );
    }

    private static class SafeInvocationHandler<T> implements InvocationHandler {
        private final T target;
        private final Callback onError;

        private SafeInvocationHandler(T target, Callback onError) {
            this.target = target;
            this.onError = onError;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(String.format("%s__safe was called", method.getName()));
            try {
                return method.invoke(target, args);
            } catch (Exception e) {
                return onError.apply(e, args);
            }
        }
    }

    public interface Callback {
        Object apply(Exception e, Object[] args) throws Throwable;
    }
}
