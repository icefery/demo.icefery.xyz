package xyz.xgh.questionnaire.questionnaire.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> R<T> success(T data) {
        return new R<T>().setCode(Code.SUCCESS.code).setMessage(Code.SUCCESS.message).setData(data);
    }

    public static <T> R<T> failure(Code e) {
        return new R<T>().setCode(e.code).setMessage(e.message).setData(null);
    }

    public static <T> R<T> failure(Code e, String message) {
        return new R<T>().setCode(e.code).setMessage(message).setData(null);
    }


    @AllArgsConstructor
    @ToString
    public enum Code {
        SUCCESS(0, "成功"),

        LOGIN_FAILURE(1001, "登录失败"),
        LOGIN_FAILURE_USERNAME_NOT_FOUND_OR_BAD_CREDENTIALS(1002, "登录失败 | 账号不存在或密码错误"),

        NOT_LOGGED_IN(2001, "访问拒绝 | 未登录"),

        TOKEN_EXPIRED(2002, "访问拒绝 | Token 过期"),
        TOKEN_INVALID(2003, "访问拒绝 | Token 错误"),

        PERMISSION_DENIED(2004, "访问拒绝 | 权限不足"),

        ARG_INVALID(4, "参数不合法"),

        CUSTOM_EXCEPTION(5, "自定义异常"),

        UNKNOWN_EXCEPTION(9, "未知异常");

        public final Integer code;
        public final String message;
    }
}
