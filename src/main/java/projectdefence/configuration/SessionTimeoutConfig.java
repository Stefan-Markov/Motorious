package projectdefence.configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebListener
public class SessionTimeoutConfig implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        // session timeout in minutes - 30min
        sce.getServletContext().setSessionTimeout(30);
    }

    protected void inactiveInterval(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        // session timeout in seconds
        session.setMaxInactiveInterval(300);
    }
}
