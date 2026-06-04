<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Creation Result</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create-order-result.css">
</head>
<body>
<div class="page-wrapper page-wrapper--narrow">

    <h1>Order Creation Status</h1>

    <div class="${not empty order ? 'success' : 'error'}">
        <strong>${message.message}</strong>
        <c:if test="${not empty message.errorCode}">
            <p>Error Code: ${message.errorCode}</p>
            <p>Technical Details: ${message.errorDetails}</p>
        </c:if>
    </div>

    <c:if test="${not empty order}">
        <div class="order-details">
            <h3>Order Details:</h3>
            <ul>
                <li><strong>Order ID:</strong> ${order.id}</li>
                <li><strong>Table:</strong> ${order.tableID}</li>
                <li><strong>Waiter:</strong> ${order.waiterID}</li>
                <li><strong>Status:</strong> ${order.status}</li>
                <li><strong>Date:</strong> ${fn:replace(fn:substring(order.dateTime, 0, 16), 'T', ' ')}</li>
            </ul>
        </div>

        <br>

        <form action="<c:url value='/order/add-items'/>" method="GET">
            <input type="hidden" name="orderID" value="${order.id}">
            <button type="submit">Add some items </button>
        </form>
    </c:if>

    <br>
    <a class="back-link" href="${pageContext.request.contextPath}/home">Back to Home</a>
    <a class="back-link order" href="${pageContext.request.contextPath}/jsp/protected/create-order.jsp">Create Another Order</a>

</div>
</body>
</html>
