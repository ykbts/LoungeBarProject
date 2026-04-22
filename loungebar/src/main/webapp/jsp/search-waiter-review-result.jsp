<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<h2>Your Reviews</h2>

<c:if test="${reviewList == null}">
    <c:redirect url="${pageContext.request.contextPath}/review/search-by-waiter"/>
</c:if>

<c:choose>
    <c:when test="${empty reviewList}">
        <p>No reviews found for your service.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
                <tr>
                    <th>Customer</th>
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
    </c:otherwise>
</c:choose>

<br/>
<p><a href="<c:url value="/jsp/home.jsp"/>">Back to Home</a></p>