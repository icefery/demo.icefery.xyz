package xyz.icefery.demo.util.function;

@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Throwable> {
    R apply(T t) throws E;
}
