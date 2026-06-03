<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login - ${role}</title>
    <link rel="stylesheet" type="text/css" href="css/base.css">
    <link rel="stylesheet" type="text/css" href="css/components.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
    <div class="container">
        <h1>Login as ${role}</h1>

        <c:if test="${not empty message}">
            <p style="color: red;">${message.message}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="hidden" name="role" value="${role}">

            <label>Username:</label><br>
            <input type="text" name="username" required><br><br>

            <label>Password:</label><br>
            <input type="password" name="password" required><br><br>

            <button type="submit">Enter</button>
        </form>
        
        <br>
        <a class="back-link" href="${pageContext.request.contextPath}/">Back to home</a>
    </div>
</body>
</html>