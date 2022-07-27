<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%-- 学生个人信息 --%>
<form action="${pageContext.request.contextPath}/student/update" method="POST">
    <table border="1px" align="center">
        <tr><td>学号</td><td><input type="text" name="stuId" readonly style="background-color: lightgray" value="${student.stuId}"></td></tr>
        <tr><td>姓名</td><td><input type="text" name="name" value="${student.name}"></td></tr>
        <tr><td>密码</td><td><input type="text" name="pwd" value="${student.pwd}"></td></tr>
        <tr><td>性别</td><td><input type="text" name="sex" value="${student.sex}"></td></tr>
        <tr><td>出生日期</td><td><input type="text" name="dob" value="${student.dob}"></td></tr>
        <tr><td>籍贯</td><td><input type="text" name="nativePlace" value="${student.nativePlace}"></td></tr>
        <tr><td>地址</td><td><input type="text" name="addr" value="${student.addr}"></td></tr>
        <tr><td>邮箱</td><td><input type="text" name="email" value="${student.email}"></td></tr>
        <tr><td>可登录</td><td><input type="text" name="enabled" readonly style="background-color: lightgray" value="${student.enabled}"></td></tr>
        <tr><td colspan="2"><button type="submit">提交</button></td></tr>
    </table>
</form>

<div class="control-wrapper" align="center">
    <c:if test="${msg != null}">
        <p style="color: red">${msg}</p>
    </c:if>
</div>