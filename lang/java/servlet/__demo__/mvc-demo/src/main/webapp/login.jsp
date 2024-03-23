<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<%-- 学生登录 --%>
<div class="stu-wrapper" style="display: block" align="center">
    <form action="${pageContext.request.contextPath}/login/stu" method="POST">
        <table>
            <tr><td>学号</td><td><input type="text" name="stuId"></td></tr>
            <tr><td>密码</td><td><input type="password" name="stuPwd"></td></tr>
            <tr><td><button type="submit">登录</button></td></tr>
        </table>
    </form>
</div>

<%-- 学生注册 --%>
<div class="register-wrapper" align="center" style="display: none">
    <form action="${pageContext.request.contextPath}/login/register" method="POST">
        <table>
            <tr><td>注册学号</td><td><input type="text" name="registerId"></td></tr>
            <tr><td>注册姓名</td><td><input type="text" name="registerName"></td></tr>
            <tr><td>注册密码</td><td><input type="password" name="registerPwd"></td></tr>
            <tr><td><button type="submit">注册</button></td></tr>
        </table>
    </form>
</div>

<%-- 管理员登录 --%>
<div class="admin-wrapper" style="display: none" align="center">
    <form action="/jsp/login/admin" method="POST">
        <table>
            <tr><td>管理员账号</td><td><input type="text" name="adminId"></td></tr>
            <tr><td>管理员密码</td><td><input type="password" name="adminPwd"></td></tr>
            <tr><td><button type="submit">登录</button></td></tr>
        </table>
    </form>
</div>

<%-- 切换和控制 --%>
<div class="control-wrapper" align="center">
    <button type="button" onclick="change()" id="login-control-button">切换学生注册</button>
    <c:if test="${msg != null}">
        <p style="color: red">${msg}</p>
    </c:if>
</div>

<script>
    function change() {
        const stuDom = document.getElementsByClassName('stu-wrapper')[0];
        const registerDom = document.getElementsByClassName('register-wrapper')[0];
        const adminDom = document.getElementsByClassName('admin-wrapper')[0];
        const controlDom = document.getElementById('login-control-button');
        if (stuDom.style.display === 'block' && registerDom.style.display === 'none' && adminDom.style.display === 'none') {
            // 学生登录 ->> 学生注册
            stuDom.style.display = 'none';
            registerDom.style.display = 'block';
            adminDom.style.display = 'none';
            controlDom.innerText = '切换管理员登录';
        } else if (stuDom.style.display === 'none' && registerDom.style.display === 'block' && adminDom.style.display === 'none') {
            // 学生注册 ->> 管理员登录
            stuDom.style.display = 'none';
            registerDom.style.display = 'none';
            adminDom.style.display = 'block';
            controlDom.innerText = '切换学生登录';
        } else if (stuDom.style.display === 'none' && registerDom.style.display === 'none' && adminDom.style.display === 'block') {
            // 管理员登录 ->> 学生登录
            stuDom.style.display = 'block';
            registerDom.style.display = 'none';
            adminDom.style.display = 'none';
            controlDom.innerText = '切换学生注册';
        }
    }
</script>