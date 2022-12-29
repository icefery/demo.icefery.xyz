package xyz.icefery.demo.util;

public record R<T>(
    String code,
    String message,
    T data
) {
    public static R<Void> success() {
        return new R<>("0", "success", null);
    }

    public static <T> R<T> success(T data) {
        return new R<>("0", "success", data);
    }

    public static R<Void> failure() {
        return new R<>("-1", "failure", null);
    }

    public static R<Void> failure(String code, String message) {
        return new R<>(code, message, null);
    }
}
