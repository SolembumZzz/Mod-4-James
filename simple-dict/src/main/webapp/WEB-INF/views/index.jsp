<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16/6/2022
  Time: 4:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Simple Dictionary</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/translate">
    <div style="float: left">
        <input type="text" placeholder="Input text here" name="keyword" value="${keyword}">
    </div>
    <div style="clear: both">
        <input value="${result}">
    </div>
    <div style="clear: both">
        <button type="submit">Search</button>
    </div>
</form>
</body>
</html>
