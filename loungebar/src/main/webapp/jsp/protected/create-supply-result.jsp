<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Create Supply Result</title>
    </head>

    <body>
        <h1>Create Supply Result</h1>
        <hr/>


        <c:if test='${not empty supply && !message.error}'>
            <h3>Details of the created supply:</h3>
            <ul>
                <li>Name: <c:out value="${supply.name}"/></li>
                <li>Units Available: <c:out value="${supply.unitsAvailable}"/></li>
            </ul>
        </c:if>

        <hr/>
        <p><a href="<c:url value="/jsp/protected/search-supply-form.jsp"/>">Add another supply</a></p>
        <p><a href="<c:url value="/home"/>">Back to Home</a></p>
    </body>
</html>