package mx.edu.utez.MiProyectoServlet.utils;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
public class RequestFilter implements Filter {

    List<String> whiteList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        whiteList.add("/");
        whiteList.add("/singin");
        whiteList.add("/login");
        whiteList.add("/register-user");
        whiteList.add("public-home");
        System.out.println("EJECUTO INIT");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String action = request.getServletPath();
        System.out.println("doFilter-> "+action);
        if (whiteList.contains(action)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            HttpSession session = request.getSession();
            if (session.getAttribute("login")!=null){
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                response.sendRedirect(request.getContextPath()+"/singin");
            }
        }
    }

    @Override
    public void destroy() {
    }
}
