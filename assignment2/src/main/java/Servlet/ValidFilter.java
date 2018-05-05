package Servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ValidFilter")
public class ValidFilter implements Filter {
    private FilterConfig filterConfig;
    private String loginPage = "adminLogin.jsp";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        String userName = (String) session.getAttribute("username");
        //user authentication
        if (null == userName) {
            req.setAttribute("err",  true);
            req.setAttribute("errMsg", "Session has ended.  Please login.");
            RequestDispatcher rd = req.getRequestDispatcher(loginPage);
            rd.forward(request, response);
        } else {
            //prevent browser cache
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
            response.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig config) throws ServletException {
        this.filterConfig = filterConfig;
    }

}
