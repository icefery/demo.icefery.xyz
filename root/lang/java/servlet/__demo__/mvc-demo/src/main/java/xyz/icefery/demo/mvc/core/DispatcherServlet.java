package xyz.icefery.demo.mvc.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import xyz.icefery.demo.mvc.annatation.Controller;
import xyz.icefery.demo.mvc.annatation.RequestMapping;
import xyz.icefery.demo.mvc.util.MvcUtil;

@Slf4j
public class DispatcherServlet extends HttpServlet {

    // 配置信息
    private final Properties configuration = new Properties();
    // bean 容器
    private final Map<String, Object> beanContainer = new HashMap<>();
    // 请求映射
    private final Map<String, Method> handlerMapping = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 加载配置
        initConfiguration(config);
        // 加载 bean 到容器中
        initBean();
        // 加载请求映射
        initHandlerMapping();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        doDispatch(req, resp);
    }

    // 加载配置
    private void initConfiguration(ServletConfig config) {
        String location = config.getInitParameter("contextConfiguration");
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(location)) {
            configuration.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 注入 bean
    private void initBean() {
        String packageName = configuration.getProperty("scan-package");
        for (String className : MvcUtil.getClassNamesFromPackage(packageName)) {
            try {
                Class<?> cls = Class.forName(className);
                if (cls.isAnnotationPresent(Controller.class)) {
                    String beanName = MvcUtil.lowerFirstCase(cls.getSimpleName());
                    beanContainer.put(beanName, cls.newInstance());

                    log.info("put bean [" + beanName + "]");
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    // 加载请求映射
    private void initHandlerMapping() {
        if (beanContainer.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : beanContainer.entrySet()) {
            Class<?> cls = entry.getValue().getClass();
            if (cls.isAnnotationPresent(Controller.class)) {
                String baseUrl = "";
                if (cls.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = cls.getAnnotation(RequestMapping.class);
                    baseUrl = requestMapping.value();
                }
                for (Method method : cls.getMethods()) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String url = ("/" + baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                        handlerMapping.put(url, method);

                        // 日志输出的方法全限定名格式为 xyz.icefery.demo.jsp.LoginController#stuLogin
                        log.info("mapped [" + url + "] with [" + cls.getName() + "#" + method.getName() + "]");
                    }
                }
            }
        }
    }

    // 分发请求
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        if (handlerMapping.isEmpty()) {
            return;
        }
        String url = req.getServletPath();
        if (handlerMapping.containsKey(url)) {
            Method method = handlerMapping.get(url);
            Class<?>[] paramTypes = method.getParameterTypes();
            Object[] paramValues = new Object[paramTypes.length];
            try {
                // 参数复制
                for (int i = 0; i < paramTypes.length; i++) {
                    if (paramTypes[i] == HttpServletRequest.class) {
                        // 设置编码
                        req.setCharacterEncoding("UTF-8");
                        paramValues[i] = req;
                    } else if (paramTypes[i] == HttpServletResponse.class) {
                        paramValues[i] = resp;
                    }
                }
                // 调用对应的 handler 处理请求
                String beanName = MvcUtil.lowerFirstCase(method.getDeclaringClass().getSimpleName());
                Object result = method.invoke(beanContainer.get(beanName), paramValues);
                if (result instanceof ModelAndView) {
                    MavResolver.resolve(req, resp, (ModelAndView) result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
