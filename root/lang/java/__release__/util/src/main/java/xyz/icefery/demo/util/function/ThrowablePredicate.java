package xyz.icefery.demo.util.function;

@FunctionalInterface
public interface ThrowablePredicate<T, E extends Throwable> {
    boolean test(T t) throws E;
}
