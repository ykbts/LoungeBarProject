<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LoungeBar - Create Shift Result</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create-shift-result.css">
</head>
<body>

<div class="page-wrapper page-wrapper--narrow">
    <h1>Create New Shift</h1>
    <p class="page-subtitle">Shift creation result</p>

    <c:choose>

        <c:when test="${not empty shift && !message.error}">
            <div class="result-card success">
                <div class="result-header">
                    <div class="result-icon">
                        <svg viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>
                    </div>
                    <div>
                        <p class="result-title">Shift created successfully</p>
                        <p class="result-message"><c:out value="${message.message}"/></p>
                    </div>
                </div>

                <div class="shift-details">
                    <table>
                        <tr>
                            <td>Shift ID</td>
                            <td><span class="shift-id-badge">#<c:out value="${shift.id}"/></span></td>
                        </tr>
                        <tr>
                            <td>Start Time</td>
                            <td>${fn:replace(fn:substring(shift.startTime, 0, 16), 'T', ' ')}</td>
                        </tr>
                        <tr>
                            <td>End Time</td>
                            <td>${fn:replace(fn:substring(shift.endTime, 0, 16), 'T', ' ')}</td>
                        </tr>
                    </table>
                </div>

                <div class="result-actions">
                    <a href="${pageContext.request.contextPath}/jsp/protected/create-shift-form.jsp" class="btn-secondary">
                        Create Another Shift
                    </a>
                    <a href="<c:url value="/home"/>" class="btn-primary">
                        Back to Home
                    </a>
                </div>
            </div>
        </c:when>

        <c:otherwise>
            <div class="result-card error">
                <div class="result-header">
                    <div class="result-icon">
                        <svg viewBox="0 0 24 24"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
                    </div>
                    <div>
                        <p class="result-title">Failed to create shift</p>
                        <p class="result-message"><c:out value="${message.message}"/></p>
                    </div>
                </div>


                <div class="result-actions">
                    <a href="${pageContext.request.contextPath}/jsp/protected/create-shift-form.jsp" class="btn-primary">
                        Try Again
                    </a>
                    <a href="<c:url value="/home"/>" class="btn-secondary">
                        Back to Home
                    </a>
                </div>
            </div>
        </c:otherwise>

    </c:choose>
</div>

</body>
</html>