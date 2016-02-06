package le.web.routing;

public class MissingRouteException extends RuntimeException {
    public MissingRouteException(String msg) {
        super(msg);
    }
}
