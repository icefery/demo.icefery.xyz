package xyz.xgh.questionnaire.questionnaire.exception;

public class CustomException extends RuntimeException {

    private final String message;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
