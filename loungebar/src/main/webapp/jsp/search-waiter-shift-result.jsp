<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<h2>Your Shifts</h2>

<c:if test="${shiftList == null}">
    <c:redirect url="${pageContext.request.contextPath}/shift/search-by-waiter"/>
</c:if>

<c:choose>
    <c:when test="${empty shiftList}">
        <p>No shifts found for your account.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="8" cellspacing="0">
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
    </c:otherwise>
</c:choose>

<br/>
<p><a href="<c:url value="/jsp/home.jsp"/>">Back to Home</a></p>