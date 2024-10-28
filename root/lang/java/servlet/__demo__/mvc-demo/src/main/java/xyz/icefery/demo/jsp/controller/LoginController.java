package xyz.icefery.demo.jsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xyz.icefery.demo.jsp.bean.Admin;
import xyz.icefery.demo.jsp.bean.Student;
import xyz.icefery.demo.jsp.dao.AdminDao;
import xyz.icefery.demo.jsp.dao.StudentDao;
import xyz.icefery.demo.mvc.annatation.Controller;
import xyz.icefery.demo.mvc.annatation.RequestMapping;
import xyz.icefery.demo.mvc.core.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    // 学生登录
    @RequestMapping(value = "/stu")
    public ModelAndView stuLogin(HttpServletRequest req, HttpServletResponse resp) {
        // 参数检查
        if (req.getParameter("stuId") == null) {
            return new ModelAndView().setViewName("index.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied!");
        }
        // 表单
        String stuId = req.getParameter("stuId").trim();
        String stuPwd = req.getParameter("stuPwd").trim();
        // 登录校验
        Student student = StudentDao.selectStudent(stuId);
        if (student == null || !student.getPwd().equals(stuPwd)) {
            return new ModelAndView()
                .setViewName("login.jsp")
                .setResponseMethod(ModelAndView.ResponseMethod.REDIRECT)
                .addObject("msg", "wrong id or password!");
        } else if (student.getEnabled() == 0) {
            return new ModelAndView().setViewName("login.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied!");
        }
        // 登录状态存入 session
        req.getSession().setAttribute("loginedUser", stuId);
        // 转发到 个人信息页面
        return new ModelAndView().setViewName("stu.jsp").setResponseMethod(ModelAndView.ResponseMethod.FORWARD).addObject("student", student);
    }

    // 注册
    @RequestMapping(value = "/register")
    public ModelAndView register(HttpServletRequest req, HttpServletResponse resp) {
        // 参数检查
        if (req.getParameter("registerId") == null) {
            return new ModelAndView().setViewName("index.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied!");
        }
        // 表单
        String registerId = req.getParameter("registerId").trim();
        String registerPwd = req.getParameter("registerPwd").trim();
        String registerName = req.getParameter("registerName").trim();
        // 注册信息校验
        if (StudentDao.selectStudent(registerId) != null) {
            return new ModelAndView()
                .setViewName("index.jsp")
                .setResponseMethod(ModelAndView.ResponseMethod.REDIRECT)
                .addObject("msg", "student already exists!");
        }
        // 插入信息
        StudentDao.insertStudent(new Student().setStuId(registerId).setPwd(registerPwd).setName(registerName));
        // 重定向到登录页
        return new ModelAndView().setViewName("login.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "registered successfully!");
    }

    // 管理员登录
    @RequestMapping(value = "/admin")
    public ModelAndView adminLogin(HttpServletRequest req, HttpServletResponse resp) {
        // 参数检查
        if (req.getParameter("adminId") == null) {
            return new ModelAndView().setViewName("index.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied!");
        }
        // 表单
        String adminId = req.getParameter("adminId").trim();
        String adminPwd = req.getParameter("adminPwd").trim();
        // 登录校验
        Admin admin = AdminDao.selectAdmin(adminId);
        if (admin == null || !admin.getPassword().equals(adminPwd)) {
            return new ModelAndView().setViewName("login.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT);
        }
        // 存 session
        req.getSession().setAttribute("loginedUser", adminId);
        // 转发到管理员管理页
        return new ModelAndView()
            .setViewName("admin.jsp")
            .setResponseMethod(ModelAndView.ResponseMethod.FORWARD)
            .addObject("studentList", StudentDao.selectStudentAll());
    }
}
