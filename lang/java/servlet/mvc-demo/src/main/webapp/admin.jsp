<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<div class="control-wrapper" align="center">
    <c:if test="${msg!=null}">
        <p style="color:red;">${msg}</p>
    </c:if>
</div>

<%-- 重置密码 --%>
<form action="${pageContext.request.contextPath}/admin/reset" method="POST">
    <table>
        <tr><td><input type="text" name="stuId"></td><td><button type="submit">重置密码</button></td></tr>
    </table>
</form>

<%-- 学生列表、授权登录 --%>
<form action="${pageContext.request.contextPath}/admin/update-student-permission" method="POST">
    <table>
        <tr>
            <td>学号</td>
            <td>姓名</td>
            <td>密码</td>
            <td>性别</td>
            <td>出生日期</td>
            <td>籍贯</td>
            <td>地址</td>
            <td>邮箱</td>
            <td>可登录</td></tr>
        <c:forEach var="student" items="${studentList}">
            <tr>
                <td><input type="text" name="stuId" readonly style="background-color: lightgray" value="${student.stuId}"></td>
                <td><input type="text" name="name" readonly style="background-color: lightgray" value="${student.name}"></td>
                <td><input type="password" name="pwd" readonly style="background-color: lightgray" value="${student.pwd}"></td>
                <td><input type="text" name="sex" readonly style="background-color: lightgray" value="${student.sex}"></td>
                <td><input type="text" name="dob" readonly style="background-color: lightgray" value="${student.dob}"></td>
                <td><input type="text" name="nativePlace" readonly style="background-color: lightgray" value="${student.nativePlace}"></td>
                <td><input type="text" name="addr" readonly style="background-color: lightgray" value="${student.addr}"></td>
                <td><input type="text" name="email" readonly style="background-color: lightgray" value="${student.email}"></td>
                <td><input type="text" name="enabled" value="${student.enabled}"></td>
                <td><button>修改</button></td>
            </tr>
        </c:forEach>
    </table>
</form>