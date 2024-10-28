package xyz.xgh.questionnaire.questionnaire.exception;

import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.xgh.questionnaire.questionnaire.util.R;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class, BindException.class })
    public R<?> validationExceptionHandler(Exception e) {
        String message = "";
        if (e instanceof ConstraintViolationException) {
            // RequestParam invalid
            message = ((ConstraintViolationException) e).getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        } else if (e instanceof MethodArgumentNotValidException) {
            // RequestBody invalid
            message = ((MethodArgumentNotValidException) e).getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
        } else if (e instanceof BindException) {
            // Type invalid
            message = ((BindException) e).getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
        }
        return R.failure(R.Code.ARG_INVALID, message);
    }

    @ExceptionHandler(CustomException.class)
    public R<?> CustomExceptionHanlder(CustomException e) {
        String message = e.getMessage();
        return R.failure(R.Code.CUSTOM_EXCEPTION, message);
    }

    @ExceptionHandler(Throwable.class)
    public R<?> defaultExceptionHanlder(Throwable e) {
        String message = e.getMessage();
        e.printStackTrace();
        return R.failure(R.Code.UNKNOWN_EXCEPTION);
    }
}
