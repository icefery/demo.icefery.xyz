package xyz.icefery.demo.util.function;

@FunctionalInterface
public interface ThrowableRunnable<E extends Throwable> {
    void run() throws E;
}
