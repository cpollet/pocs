package net.cpollet.pocs.restsafe;

import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private RemoteService remoteService;

    public static void main(String[] args) {
        try {
            new Main().run(new RemoteServiceImpl());
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void run(RemoteServiceImpl remoteService) {
        // level 1 - direct call
        // remoteService.iterator().forEachRemaining(System.out::println);

        // level 2 - call through a proxy
//        RemoteService remoteServiceProxy = (RemoteService) Proxy.newProxyInstance(
//                getClass().getClassLoader(),
//                new Class[]{RemoteService.class},
//                new DynamicInvocationHandler(remoteService)
//        );
//
//        System.out.println(remoteServiceProxy.toInteger("42"));
//        System.out.println(remoteServiceProxy.toInteger("nan"));

        // level 3 - call through a safe proxy
//        SafeRemoteServiceProxy<RemoteService> safeRemoteServiceProxy = new SafeRemoteServiceProxy<>(remoteService, RemoteService.class);
//
//        System.out.println(safeRemoteServiceProxy.build((e, a) -> -1).toInteger("42"));
//        System.out.println(safeRemoteServiceProxy.build((e, a) -> -1).toInteger("nan"));

        // level 4 - ...
        System.out.println(call(
                () -> remoteService.toInteger("42"),
                result -> Result.success(result * 100),
                e -> Result.<Integer>failure()
        ).getValue());

        System.out.println("--");
        System.out.println(call(
                () -> remoteService.toInteger("nan"),
                result -> Result.success(result * 100),
                e -> Result.<Integer>failure()
        ).getValue());

        System.out.println("--");
        System.out.println(call(
                () -> remoteService.toInteger("nan"),
                result -> Result.success(result * 100),
                e -> Result.failure(-2)
        ).getValue());

        System.out.println("--");
        System.out.println(call(
                () -> remoteService.toInteger("nan"),
                result -> Result.success(result * 100),
                e -> Result.failure(-3),
                e -> Result.failure(-4)
        ).getValue());

        System.out.println("--");
        System.out.println(call(
                () -> remoteService.toInteger("nan"),
                e -> -5
        ));

        System.out.println("--");
        try {
            System.out.println(call(
                    () -> remoteService.toInteger("nan")
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <R> R call(Supplier<R> supplier) throws Exception {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw e;
        }
    }

    private <R> R call(Supplier<R> supplier, Function<Exception, R> onError) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return onError.apply(e);
        }
    }

    private <R, T> R call(Supplier<T> supplier, Function<T, R> onSuccess, Function<Exception, R> onError) {
        try {
            return onSuccess.apply(supplier.get());
        } catch (Exception e) {
            return onError.apply(e);
        }
    }

    private <R, T> R call(Supplier<T> supplier, Function<T, R> onSuccess, Function<Exception, R> onClientError, Function<Exception, R> onServerError) {
        try {
            return onSuccess.apply(supplier.get());
        } catch (Exception e) {
            switch (e.getClass().getName()) {
                case "java.lang.Exception":
                    return onClientError.apply(e);
                default:
                    return onServerError.apply(e);
            }
        }
    }

    static class Result<T> {
        private static final Result FAILURE = new Result<>(null, false);
        private final T value;
        private final boolean success;

        private Result(T value, boolean success) {
            this.value = value;
            this.success = success;
        }

        static <T> Result<T> success(T value) {
            return new Result<>(value, true);
        }

        @SuppressWarnings("unchecked")
        static <T> Result<T> failure() {
            return FAILURE;
        }

        static <T> Result<T> failure(T value) {
            return new Result<>(value, false);
        }

        T getValue() {
            return value;
        }

        private boolean isSuccess() {
            return success;
        }
    }
}
