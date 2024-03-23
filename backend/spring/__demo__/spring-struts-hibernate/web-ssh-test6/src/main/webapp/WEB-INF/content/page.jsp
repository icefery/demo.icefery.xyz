<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" %> <%@ taglib prefix="s" uri="/struts-tags" %>

<title>page.jsp</title>

<s:form namespace="/book" action="query-by-id" method="GET">
  <s:textfield name="bookToQuery.id" />
  <s:submit value="根据ID查询" />
</s:form>

<table>
  <thead>
    <tr>
      <th>ID</th>
      <th>名称</th>
      <th>书号</th>
      <th>作者</th>
      <th>出版社</th>
      <th>价格</th>
      <th>Operation</th>
    </tr>
  </thead>
  <tbody>
    <s:iterator value="%{bookList}" var="book">
      <s:form namespace="/book" action="modify-by-id">
        <tr>
          <td>
            <s:textfield value="%{#book.id}" disabled="true" />
            <s:textfield name="bookToModify.id" value="%{#book.id}" hidden="true" />
          </td>
          <td><s:textfield name="bookToModify.name" value="%{#book.name}" /></td>
          <td><s:textfield name="bookToModify.code" value="%{#book.code}" /></td>
          <td><s:textfield name="bookToModify.author" value="%{#book.author}" /></td>
          <td><s:textfield name="bookToModify.press" value="%{#book.press}" /></td>
          <td><s:textfield name="bookToModify.price" value="%{#book.price}" /></td>
          <td><s:submit value="修改" /></td>
        </tr>
      </s:form>
    </s:iterator>
  </tbody>
  <tfoot>
    <s:form namespace="/book" action="save" method="GET">
      <tr>
        <td><s:textfield placeholder="自动生成ID" disabled="true" /></td>
        <td><s:textfield name="bookToSave.name" /></td>
        <td><s:textfield name="bookToSave.code" /></td>
        <td><s:textfield name="bookToSave.author" /></td>
        <td><s:textfield name="bookToSave.press" /></td>
        <td><s:textfield name="bookToSave.price" /></td>
        <td><s:submit value="添加" /></td>
      </tr>
    </s:form>
  </tfoot>
</table>
