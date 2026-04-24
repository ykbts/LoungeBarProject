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

        <h1>Manage Order</h1>

        <div class="${empty message.errorCode ? 'success' : 'error'}">
            <strong>${message.message}</strong>
            <c:if test="${not empty message.errorCode}">
                <p>Error Code: ${message.errorCode}</p>
                <p>Technical Details: ${message.errorDetails}</p>
            </c:if>
        </div>

        <h2>
            Add items
        </h2>
        <div>
            <form id="addItemForm">
                
                <input type="hidden" name="orderID" value="${order.id}">

                <label>Product:</label>
                <select name="productID">
                    <c:forEach var="product" items="${menu}">
                        <option value="${product.name}">${product.name} - €${product.price}</option>
                    </c:forEach>
                </select>

                <label>Quantity:</label>
                <input type="number" name="quantity" value="1" min="1">

                <button type="button" onclick="addItem()">Add to Order</button>

            </form>
        </div>
        
        <div>
            <h3>Items added:</h3>
            <ul id="itemList">

            </ul>
        </div>

    
        <script type="text/javascript" src="<c:url value='/js/add-items.js'/>"></script>
    </body>
</html>