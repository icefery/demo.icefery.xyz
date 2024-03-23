package xyz.icefery.demo.mvc.core;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MavResolver {
    public static void resolve(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws ServletException, IOException {
        String contextPath = req.getContextPath();
        switch (mav.getResponseMethod()) {
            case FORWARD: {
                // 注入 key-value 到 request 中
                mav.setOfModelMap().forEach(entry -> req.setAttribute(entry.getKey(), entry.getValue()));
                // 格式化转发路径 /context-path/view-name
                String url = ("/" + mav.getViewName()).replaceAll("/+", "/");
                req.getRequestDispatcher(url).forward(req, resp);
                break;
            }
            case REDIRECT: {
                // 格式化重定向路径
                StringBuilder url;
                if (mav.getViewName().startsWith("http") || mav.getViewName().startsWith("www.")) {
                    url = new StringBuilder(mav.getViewName());
                } else {
                    url = new StringBuilder(("/" + contextPath + "/" + mav.getViewName()).replaceAll("/+", "/"));
                }
                // 参数拼接到 url 中
                mav.setOfModelMap().forEach(entry -> url.append("?").append(entry.getKey()).append("=").append(entry.getValue()));
                resp.sendRedirect(String.valueOf(url));
                break;
            }
            case PLAIN: {
                // TODO
            }
            default: {

            }
        }
    }
}
