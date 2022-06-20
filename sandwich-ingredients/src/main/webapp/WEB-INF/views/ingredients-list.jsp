<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Condiments List</title>
</head>
<body>
<h1>Condiments List</h1>
<table>
    <c:forEach var="ingredient" items="${ingredients}">
        <tr>
            <td>${ingredient}</td>
        </tr>
    </c:forEach>
    <tr>
        <td>
            <a href="${pageContext.request.contextPath}/">Return</a>
        </td>
    </tr>
</table>
</body>
</html>