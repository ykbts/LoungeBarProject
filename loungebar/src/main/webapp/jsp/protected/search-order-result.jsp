<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Orders of the Bar</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
<%--    <link rel="stylesheet" href="<c:url value='/css/base.css'/>">--%>
<%--    <link rel="stylesheet" href="<c:url value="/css/search-order-result.css"/>">--%>
    <link rel="stylesheet" href="<c:url value='/css/search-order-result.css'/>">
</head>

    <body>
        <div class="title">
            <h1>List of Orders</h1>
        </div>

        <c:if test="${not empty orderList}">
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Date</th>
                            <th>Waiter Name</th>
                            <th>Status</th>
                            <th>Table</th>
                            <th>Pay Method</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${orderList}">
                            <tr>
                                <td>
                                    <a href="<c:url value='/order/add-items'/>?orderID=${order.id}">
                                            ${order.id}
                                    </a>
                                </td>
                                <td>${order.dateTime}</td>
                                <td>${order.waiterID}</td>
                                <td id="status-cell-${order.id}">${order.status}</td>
                                <td>${order.tableID}</td>
                                <td id="pay-method-cell-${order.id}">${order.payMethod}</td>
                                <td id="action-cell-${order.id}">
                                <c:if test="${order.status == 'PENDING'}">
                                    <select id="pay-method-${order.id}" style="margin-bottom: 5px;">
                                        <option value="CASH">Cash</option>
                                        <option value="BANCOMAT">Bancomat</option>
                                        <option value="DEBIT_CARD">Debit Card</option>
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
            </div>
        </c:if>

        <c:if test="${empty orderList}">
            <p>No orders found.</p>
        </c:if>

        <br/>
        <a href="${pageContext.request.contextPath}/home">Back to Home</a>

        <script type="text/javascript" src="<c:url value='/js/update-order.js'/>"></script>
    </body>
</html>