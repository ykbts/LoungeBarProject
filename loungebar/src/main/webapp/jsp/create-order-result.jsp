<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order Creation Result</title>
    <style>
        .success { color: #155724; background: #d4edda; padding: 15px; border: 1px solid #c3e6cb; }
        .error { color: #721c24; background: #f8d7da; padding: 15px; border: 1px solid #f5c6cb; }
        .details { margin-top: 20px; font-family: monospace; background: #eee; padding: 10px; }
    </style>
</head>
<body>

    <h1>Order Creation Status</h1>

    <div class="${not empty order ? 'success' : 'error'}">
        <strong>${message.message}</strong>
        <c:if test="${not empty message.errorCode}">
            <p>Error Code: ${message.errorCode}</p>
            <p>Technical Details: ${message.errorDetails}</p>
        </c:if>
    </div>

    <c:if test="${not empty order}">
        <div class="details">
            <h3>Order Details:</h3>
            <ul>
                <li><strong>Order ID:</strong> ${order.id}</li>
                <li><strong>Table:</strong> ${order.tableID}</li>
                <li><strong>Waiter:</strong> ${order.waiterID}</li>
                <li><strong>Status:</strong> ${order.status}</li>
                <li><strong>Date:</strong> ${order.dateTime}</li>
            </ul>
        </div>
    </c:if>

    <br>
    <a href="${pageContext.request.contextPath}/jsp/home.jsp">Return to Home</a>
    <a href="${pageContext.request.contextPath}/jsp/create-order.jsp">Create Another Order</a>

</body>
</html>