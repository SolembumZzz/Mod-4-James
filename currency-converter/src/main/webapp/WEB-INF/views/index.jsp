<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 16/6/2022
  Time: 10:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency Converter</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/doTheThing">
    <table>
        <tr>
            <th>VND</th>
            <td>
                <input type="number" name="original" value="${requestScope.original}">
            </td>
            <td>
                <button>Convert</button>
            </td>
        </tr>
        <tr>
            <th>USD</th>
            <td>
                <input type="number" name="converted" readonly value="${requestScope.converted}">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
