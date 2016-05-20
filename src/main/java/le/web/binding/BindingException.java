package le.web.binding;

public class BindingException extends RuntimeException {

    public BindingException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

    public BindingException(String message) {
        super(message);
    }
}
