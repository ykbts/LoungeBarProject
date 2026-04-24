<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Client Order</title>
        <style>
            .success { color: #155724; background: #d4edda; padding: 15px; border: 1px solid #c3e6cb; }
            .error { color: #721c24; background: #f8d7da; padding: 15px; border: 1px solid #f5c6cb; }
            .details { margin-top: 20px; font-family: monospace; background: #eee; padding: 10px; }
        </style>
    </head>

    <body>
        <h1>Review my order</h1>
        <hr/>

        <div class="${not empty orderItemList ? 'success' : 'error'}">
        <strong>${message.message}</strong>
        <c:if test="${not empty message.errorCode}">
            <p>Error Code: ${message.errorCode}</p>
            <p>Technical Details: ${message.errorDetails}</p>
        </c:if>
        </div>


        <c:if test="${not empty orderItemList}">
            <div class="details">
                <h3>Order Details:</h3>
                <ul id="result">
                    <c:forEach var="order" items="${orderItemList}"></c:forEach>
                        <li>
                            <span>${order.drinkName}</span>
                            <span>${order.quantity}</span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        <hr/>


        <p><a href="<c:url value="/home"/>">Back to Home</a></p>

        <script>

        </script>
    </body>
</html>