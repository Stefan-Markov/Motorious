package projectdefence.security.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestValidationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String id = httpRequest.getSession().getId();
        if (id == null) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ((HttpServletResponse) response).sendRedirect("/user/login");
        }
        filterChain.doFilter(request, response);
    }
}
