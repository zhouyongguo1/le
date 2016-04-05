package le.web;

import com.google.common.base.Strings;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@Singleton
public class HttpMethodFilter implements Filter {

    public static final String PARAMETER_NAME = "_method";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String paramValue = request.getParameter(PARAMETER_NAME);
        String method;
        if (!Strings.isNullOrEmpty(paramValue)) {
            method = paramValue.toUpperCase();
        } else {
            method = ((HttpServletRequest) request).getMethod();
        }
        HttpServletRequest wrapper = new HttpMethodRequestWrapper(servletRequest, method);
        filterChain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {
    }

    private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

        private String method;

        public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public String getContentType() {
            String contentType = super.getContentType();
            if (contentType != null) {
                return contentType;
            }
            return "application/x-www-form-urlencoded";
        }
    }

}
