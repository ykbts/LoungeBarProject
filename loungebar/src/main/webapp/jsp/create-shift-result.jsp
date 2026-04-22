<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Create Shift Result</title>
    </head>

    <body>
        <h1>Create Shift Result</h1>
        <hr/>

        <c:if test='${not empty shift && !message.error}'>
            <h3>Details of the created shift:</h3>
            <ul>
                <li><strong>Shift ID:</strong> <c:out value="${shift.id}"/></li>
                <li><strong>Start Time:</strong> <c:out value="${shift.startTime}"/></li>
                <li><strong>End Time:</strong> <c:out value="${shift.endTime}"/></li>
            </ul>
            <p style="color: green;">${message.message}</p>
        </c:if>

        <c:if test='${message.error}'>
            <h3 style="color: red;">Error!</h3>
            <p><c:out value="${message.message}"/></p>
            <c:if test='${not empty message.errorCode}'>
                <p><small>Error Code: <c:out value="${message.errorCode}"/></small></p>
            </c:if>
        </c:if>

        <hr/>

            <a href="${pageContext.request.contextPath}/jsp/create-shift-form.jsp">Create Another Shift</a>
            <p><a href="<c:url value="/jsp/home.jsp"/>">Back to Home</a></p>
    </body>
</html>