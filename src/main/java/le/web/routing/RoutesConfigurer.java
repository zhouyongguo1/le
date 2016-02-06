package le.web.routing;

import com.google.inject.matcher.Matchers;
import le.reflect.Classes;
import le.web.annotation.Controller;
import le.web.annotation.Route;
import le.web.annotation.http.Delete;
import le.web.annotation.http.Get;
import le.web.annotation.http.Post;
import le.web.annotation.http.Put;
import ninja.Router;
import ninja.application.ApplicationRoutes;

import java.lang.reflect.Method;
import java.util.Set;

public abstract class RoutesConfigurer implements ApplicationRoutes {

    private Router router;

    @Override
    public final void init(final Router router) {
        this.router = router;
        configure();
    }

    public Router getRouter() {
        return router;
    }

    protected abstract void configure();

    protected void scan(Package pkg) {
        Set<Class<?>> classes = Classes.matching(Matchers.annotatedWith(Controller.class)).in(pkg);
        for (Class<?> controllerClass : classes) {
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Get.class)) {
                    router.GET().route(getRoute(method)).with(controllerClass, method.getName());
                } else if (method.isAnnotationPresent(Post.class)) {
                    router.POST().route(getRoute(method)).with(controllerClass, method.getName());
                } else if (method.isAnnotationPresent(Delete.class)) {
                    router.DELETE().route(getRoute(method)).with(controllerClass, method.getName());
                } else if (method.isAnnotationPresent(Put.class)) {
                    router.PUT().route(getRoute(method)).with(controllerClass, method.getName());
                }
            }
        }
    }

    private String getRoute(Method method) {
        if (!method.isAnnotationPresent(Route.class)) {
            String msg = String.format("Could not determine route for %s::%s",
                    method.getDeclaringClass().getName(), method.getName());
            throw new MissingRouteException(msg);
        }
        return method.getAnnotation(Route.class).value();
    }
}
