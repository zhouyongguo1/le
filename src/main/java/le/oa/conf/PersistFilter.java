package le.oa.conf;

import com.google.inject.Inject;
import com.google.inject.persist.UnitOfWork;

import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Singleton
final class PersistFilter implements Filter {

    private final UnitOfWork unitOfWork;

    @Inject
    public PersistFilter(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {

        unitOfWork.begin();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            unitOfWork.end();
        }
    }
}
