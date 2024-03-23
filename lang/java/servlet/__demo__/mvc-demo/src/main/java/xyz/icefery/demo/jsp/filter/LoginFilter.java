package xyz.icefery.demo.jsp.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        String contextPath = request.getContextPath() + "/";
        Object loginedUser = request.getSession().getAttribute("loginedUser");

        if (uri.endsWith("index.jsp") || uri.endsWith(contextPath)) {
            // 放行首页
            filterChain.doFilter(request, response);
        } else if (uri.endsWith("login.jsp")) {
            // 放行登录页面
            filterChain.doFilter(request, response);
        } else if (loginedUser != null) {
            // 放行登录状态下请求
            filterChain.doFilter(request, response);
        } else if (uri.endsWith("/login/stu") || uri.endsWith("/login/admin") || uri.endsWith("/login/register")) {
            // 放行登录、注册请求
            filterChain.doFilter(request, response);
        } else {
            // 拦截其它资源，其它请求
            String formatRedirectUri = contextPath + "index.jsp" + "?msg=permission denied";
            response.sendRedirect(formatRedirectUri);
        }
    }

    @Override
    public void destroy() {

    }
}
