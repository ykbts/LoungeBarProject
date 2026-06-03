<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign-up - ${role}</title>
    <link rel="stylesheet" type="text/css" href="css/base.css">
    <link rel="stylesheet" type="text/css" href="css/components.css">
    <link rel="stylesheet" type="text/css" href="css/signup.css">
</head>
<body>
    <div class="container">
        <h1>Register as ${role}</h1>

        <c:if test="${not empty message}">
            <p style="color: red;">${message.message}</p>
        </c:if>

        <form action="${pageContext.request.contextPath}/signup" method="post">
            <input type="hidden" name="role" value="${role}">

            <label>Username:</label><br>
            <input type="text" name="username" minlength="3" required><br><br>

            <label>Password:</label><br>
            <input type="password" name="password" required><br><br>

            <label>Repeat Password:</label><br>
            <input type="password" name="password_check" required><br><br>

            <label>First Name:</label><br>
            <input type="text" name="firstname" required><br><br>

            <label>Surname:</label><br>
            <input type="text" name="surname"><br><br>

            <label>Phone:</label><br>
            <input type="text" name="phone"><br><br>

            <button type="submit">Create Account</button>
        </form>

        <br>
        <a class="back-link" href="${pageContext.request.contextPath}/">Back to home</a>

    </div>
</body>
</html>