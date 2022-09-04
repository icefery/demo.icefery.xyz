<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<title>这首页啥都没有~</title>
<h2>Hello World!</h2>

<ul>
    <li><a href="http://localhost:8080/jsp/login.jsp">登录界面</a></li>
    <li><a href="http://localhost:8080/jsp/stu.jsp">未登录访问资源</a></li>
    <li><a href="http://localhost:8080/jsp/login/stu">访问处理器</a></li>
</ul>


<%-- 重定向到首页时携带的信息 --%>
<div class="control-wrapper" align="center">
    <c:if test="${msg != null}" >
        <p style="color: red">${msg}</p>
    </c:if>
</div>


