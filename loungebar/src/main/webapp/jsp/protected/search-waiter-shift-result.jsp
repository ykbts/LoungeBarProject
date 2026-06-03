<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Shifts</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/components.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/search-waiter-shift-result.css'/>">
</head>
<body>
<div class="page-wrapper page-wrapper--narrow">

    <h2>Your Shifts</h2>

    <c:if test="${shiftList == null}">
        <c:redirect url="${pageContext.request.contextPath}/shift/search-by-waiter"/>
    </c:if>

    <c:choose>
        <c:when test="${empty shiftList}">
            <p>No shifts found for your account.</p>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Shift ID</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="shift" items="${shiftList}">
                            <tr>
                                <td>${shift.id}</td>
                                <td>${shift.formattedStartTime}</td>
                                <td>${shift.formattedEndTime}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

    <p><a class="back-link" href="<c:url value="/home"/>">Back to Home</a></p>

</div>
</body>
</html>