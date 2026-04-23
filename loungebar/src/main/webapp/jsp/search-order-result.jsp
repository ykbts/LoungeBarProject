<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Orders</title>
</head>
<body style="padding: 30px;"> 
    <h2 style="margin-bottom: 25px;">Orders assigned to you:</h2>

    <c:if test="${not empty orderList}">
        <table border="1" cellpadding="12" cellspacing="0" style="width: 100%; max-width: 800px;">
            <thead>
                <tr style="background-color: #eeeeee;">
                    <th>ID</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Table</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.dateTime}</td>
                        <td id="status-cell-${order.id}">${order.status}</td>
                        <td>${order.tableID}</td>
                        <td id="action-cell-${order.id}">
                        <c:if test="${order.status == 'PENDING'}">
                            <select id="pay-method-${order.id}" style="margin-bottom: 5px;">
                                <option value="CASH">Cash</option>
                                <option value="BANCOMAT">Bancomat</option>
                                <option value="DEBIT CARD">Debit Card</option>
                            </select>
                            <br/>
                            <button type="button" 
                                    id="pay-btn-${order.id}" 
                                    onclick="changeStatus('${order.id}')">
                                Confirm Payment
                            </button>
                        </c:if>
                    </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty orderList}">
        <p>No orders found.</p>
    </c:if>

    <br/>
    <a href="<c:url value='/index.jsp'/>">Back to Home</a>

    <script type="text/javascript" src="<c:url value='/js/update-order.js'/>"></script>
</body>
</html>