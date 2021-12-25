package xyz.icefery.test.servlet;

import xyz.icefery.test.dao.CustomerDAO;
import xyz.icefery.test.entity.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class CustomerServlet {
    private static Boolean validateGender(String gender, Consumer<Integer> onValid) {
        if (Objects.equals(gender, "1") || Objects.equals(gender, "2")) {
            if (onValid != null) {
                onValid.accept(Integer.parseInt(gender));
            }
            return true;
        }
        return true;
    }

    private static Boolean validateBirthDate(String birthDate, Consumer<LocalDate> onValid) {
        try {
            LocalDate date = LocalDate.parse(birthDate);
            if (onValid != null) {
                onValid.accept(date);
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    private static Boolean validatePhone(String phone, Consumer<String> onValid) {
        if (Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", phone)) {
            if (onValid != null) {
                onValid.accept(phone);
            }
            return true;
        }
        return false;
    }

    @WebServlet({"/customer/create"})
    public static class CreateServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            // TODO: http method validation
            String name = request.getParameter("name");
            String gender = request.getParameter("gender");
            String birthDate = request.getParameter("birthDate");
            String phone = request.getParameter("phone");
            String occupation = request.getParameter("occupation");
            String note = request.getParameter("note");
            
            // 记录参数
            System.out.println("[CreateServlet]" + "name=" + name + "\tgender=" + gender + "\tbirthDate=" + birthDate + "\tphone=" + phone + "\toccupation=" + occupation + "\tnote=" + note);

            Customer customer = new Customer();

            // 姓名: !null && !empty
            if (name != null && !name.isEmpty()) {
                customer.setName(name);
            } else {
                request.setAttribute("errorMessage", "姓名不能为空");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }

            // 性别: '' -> null || '1' || '2'
            if (!(gender == null || gender.isEmpty() || validateGender(gender, customer::setGender))) {
                request.setAttribute("errorMessage", "性别不合法");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }

            // 出生日期: '' -> null  || LocalDate
            if (!(birthDate == null || birthDate.isEmpty() || validateBirthDate(birthDate, customer::setBirthDate))) {
                request.setAttribute("errorMessage", "出生日期不合法");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }

            // 手机号: '' -> null || Regex
            if (!(phone == null || phone.isEmpty() || validatePhone(phone, customer::setPhone))) {
                request.setAttribute("errorMessage", "手机号不合法");
                request.getRequestDispatcher("/").forward(request, response);
                return;
            }

            // 职业: '' -> null
            if (occupation != null && !occupation.isEmpty()) {
                customer.setOccupation(occupation);
            }

            // 备注: '' -> null
            if (note != null && !note.isEmpty()) {
                customer.setNote(note);
            }

            CustomerDAO.create(customer);
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    @WebServlet({"/", "/customer/query_list"})
    public static class ListServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // TODO: http method validation
            String name = request.getParameter("name");
            String gender = request.getParameter("gender");
            String birthDate = request.getParameter("birthDate");

            // 记录参数
            System.out.println("[ListServlet]" + "name=" + name + "\tgender" + gender + "\tbirthDate=" + birthDate);

            List<Customer> list;

            if (name != null && !name.isEmpty()) {
                list = CustomerDAO.queryListByName(name);
            } else if (gender != null && !gender.isEmpty()) {
                // 性别: '1' || '2'
                if (!validateGender(gender, null)) {
                    request.setAttribute("errorMessage", "性别不合法");
                    request.getRequestDispatcher("/").forward(request, response);
                    return;
                }
                list = CustomerDAO.queryListByGender(Integer.parseInt(gender));
            } else if (birthDate != null && !birthDate.isEmpty()) {
                // 出生日期: LocalDate
                if (!validateBirthDate(birthDate, null)) {
                    request.setAttribute("errorMessage", "出生日期不合法");
                    request.getRequestDispatcher("/").forward(request, response);
                    return;
                }
                list = CustomerDAO.queryListByBirthDate(LocalDate.parse(birthDate));
            } else {
                list = CustomerDAO.queryList();
            }
            request.setAttribute("list", list);
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }
    }

}
