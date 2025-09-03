package xyz.icefery.demo.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.icefery.demo.util.R;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public R<?> throwable(Throwable e) {
        e.printStackTrace();
        return R.failure("-1", e.getMessage());
    }
}
