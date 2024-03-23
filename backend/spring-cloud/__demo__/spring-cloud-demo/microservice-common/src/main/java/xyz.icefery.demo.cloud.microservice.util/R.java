package xyz.icefery.demo.cloud.microservice.util;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> R<T> success(T data) {
        return new R<T>().setCode(0).setMessage("").setData(data);
    }

    public static <T> R<T> failure(Integer code, String message) {
        return new R<T>().setCode(code).setMessage(message).setData(null);
    }
}