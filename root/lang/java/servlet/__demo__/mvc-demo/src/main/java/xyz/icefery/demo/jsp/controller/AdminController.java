package xyz.icefery.demo.jsp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xyz.icefery.demo.jsp.dao.StudentDao;
import xyz.icefery.demo.mvc.annatation.Controller;
import xyz.icefery.demo.mvc.annatation.RequestMapping;
import xyz.icefery.demo.mvc.core.ModelAndView;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "/reset")
    public ModelAndView reset(HttpServletRequest req, HttpServletResponse resp) {
        // 参数检查
        if (req.getParameter("stuId") == null) {
            return new ModelAndView().setViewName("index.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied!");
        }
        // 重置密码
        StudentDao.resetStudentPassword(req.getParameter("stuId").trim());
        // 转发到管理页
        return new ModelAndView().setViewName("admin.jsp").setResponseMethod(ModelAndView.ResponseMethod.FORWARD).addObject("msg", "reset successfully");
    }

    @RequestMapping(value = "/update-student-permission")
    public ModelAndView updateStudentPermission(HttpServletRequest req, HttpServletResponse resp) {
        // 参数检查
        if (req.getParameter("stuId") == null) {
            return new ModelAndView().setViewName("index.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied");
        }
        // 授权登录
        StudentDao.updateStudentPermission(req.getParameter("stuId").trim(), Integer.valueOf(req.getParameter("enabled").trim()));
        return new ModelAndView()
            .setViewName("admin.jsp")
            .setResponseMethod(ModelAndView.ResponseMethod.FORWARD)
            .addObject("studentList", StudentDao.selectStudentAll())
            .addObject("msg", "update successfully!");
    }
}
