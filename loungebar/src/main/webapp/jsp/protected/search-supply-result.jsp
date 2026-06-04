<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LoungeBar - Supply List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search-supply-result.css">
</head>
<body>

<div class="page-wrapper">

    <div class="table-centered">

        <c:if test="${not empty supplyList && !message.error}">
            <h2 class="page-heading">Supply Inventory</h2>
            <p class="page-subtitle">${supplyList.size()} items found</p>

            <table class="supply-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Units Available</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="supply" items="${supplyList}">
                        <tr>
                            <td><c:out value="${supply.name}"/></td>
                            <td><c:out value="${supply.unitsAvailable}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${empty supplyList && !message.error}">
            <h2 class="page-heading">Supply Inventory</h2>
            <p class="page-subtitle">The inventory is empty or no supplies match your filters.</p>
        </c:if>

    <p><a class="back-link search" href="<c:url value="/supply/search"/>">Back to Search</a></p>
    <p><a class="back-link" href="<c:url value="/home"/>">Back to Home</a></p>

    </div>
</div>
</body>
</html>