<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Reviews</title>
    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/components.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/search-waiter-review-result.css'/>">
</head>
<body>
<div class="page-wrapper page-wrapper--narrow">

    <h2>Your Reviews</h2>

    <c:if test="${reviewList == null}">
        <c:redirect url="${pageContext.request.contextPath}/review/search-by-waiter"/>
    </c:if>

    <c:choose>
        <c:when test="${empty reviewList}">
            <p>No reviews found for your service.</p>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table>
                    <thead>
                        <tr>
                            <th>Customer ID</th>
                            <th>Score</th>
                            <th>Comment</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="review" items="${reviewList}">
                            <tr>
                                <td><c:out value="${review.customerID}"/></td>
                                <td><strong><c:out value="${review.score}"/> / 5</strong></td>
                                <td><c:out value="${review.comment}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>

    <p><a href="<c:url value="/home"/>">Back to Home</a></p>

</div>
</body>
</html>