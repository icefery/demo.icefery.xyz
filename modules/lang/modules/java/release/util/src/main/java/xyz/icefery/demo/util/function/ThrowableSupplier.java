package xyz.icefery.demo.util.function;

@FunctionalInterface
public interface ThrowableSupplier<T, E extends Throwable> {
    T get() throws E;
}
