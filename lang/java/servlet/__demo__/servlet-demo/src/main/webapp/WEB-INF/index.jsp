<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<p style="color: #FF0000">${errorMessage}</p>

<table>
  <tr>
    <th>姓名</th>
    <th>性别</th>
    <th>出生日期</th>
    <th>手机号</th>
    <th>职业</th>
    <th>备注</th>
    <th>操作</th>
  </tr>
  <tr>
    <form action="${pageContext.request.contextPath}/customer/create" method="POST">
      <td><input name="name" /></td>
      <td><input type="radio" name="gender" value="1" />男<input type="radio" name="gender" value="2" />女</td>
      <td><input type="date" name="birthDate" /></td>
      <td><input name="phone" /></td>
      <td><input name="occupation" /></td>
      <td><input name="note" /></td>
      <td>
        <button type="submit">创建</button>
      </td>
    </form>
  </tr>
  <tr>
    <form action="${pageContext.request.contextPath}/customer/query_list" method="POST">
      <td><input name="name" /></td>
      <td><input type="radio" name="gender" value="1" />男<input type="radio" name="gender" value="2" />女</td>
      <td><input type="date" name="birthDate" /></td>
      <td colspan="3" style="background-color: #AAAAAA">仅支持单条件等值查询</td>
      <td>
        <button type="submit">查询</button>
      </td>
    </form>
  </tr>

  <c:forEach items="${list}" var="c">
    <tr>
      <td>${c.name}</td>
      <td>${c.gender == null ? "未设置" : (c.gender == 1 ? "男" : "女") }</td>
      <td>${c.birthDate == null ? "未设置" : c.birthDate}</td>
      <td>${c.phone == null ? "未设置" : c.phone}</td>
      <td>${c.occupation == null ? "未设置" : c.occupation}</td>
      <td>${c.note == null ? "未设置" : c.note}</td>
    </tr>
  </c:forEach>
</table>


<style>
  table, tr, th, td {
    border: 1px solid #000000;
    text-align: center;
  }
</style>