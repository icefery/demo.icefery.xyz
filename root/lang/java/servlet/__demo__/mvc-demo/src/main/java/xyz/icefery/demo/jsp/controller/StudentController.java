package xyz.icefery.demo.jsp.controller;

import java.io.IOException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xyz.icefery.demo.jsp.bean.Student;
import xyz.icefery.demo.jsp.dao.StudentDao;
import xyz.icefery.demo.mvc.annatation.Controller;
import xyz.icefery.demo.mvc.annatation.RequestMapping;
import xyz.icefery.demo.mvc.core.ModelAndView;

@Controller
@RequestMapping(value = "/student")
public class StudentController {

    // 更新
    @RequestMapping(value = "/update")
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 参数检查
        if (req.getParameter("stuId") == null) {
            return new ModelAndView().setViewName("index.jsp").setResponseMethod(ModelAndView.ResponseMethod.REDIRECT).addObject("msg", "permission denied");
        }
        // 表单
        Student student = new Student()
            .setStuId(req.getParameter("stuId").trim())
            .setName(req.getParameter("name").trim())
            .setPwd(req.getParameter("pwd").trim())
            .setSex(Integer.valueOf(req.getParameter("sex").trim()))
            .setDob(Timestamp.valueOf(req.getParameter("dob").trim()))
            .setNativePlace(req.getParameter("nativePlace").trim())
            .setAddr(req.getParameter("addr").trim())
            .setEmail(req.getParameter("email").trim())
            .setEnabled(Integer.valueOf(req.getParameter("enabled").trim()));
        // 数据更新
        StudentDao.updateStudent(student);
        // 新的数据
        Student refreshedStudent = StudentDao.selectStudent(req.getParameter("stuId").trim());
        // 重定向到学生个人信息
        return new ModelAndView()
            .setViewName("stu.jsp")
            .setResponseMethod(ModelAndView.ResponseMethod.FORWARD)
            .addObject("student", refreshedStudent)
            .addObject("msg", "update successfully!");
    }
}
