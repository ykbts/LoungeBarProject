<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Order Creation Result</title>

        <!-- Fonts -->
         <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@300;400;600&family=DM+Sans:wght@300;400;500&display=swap">
        <!-- Style -->
        <link rel="stylesheet" href="../css/base.css">
        <link rel="stylesheet" href="../css/add-items.css">
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
                
                <input type="hidden" name="orderID" value="${param.orderID}">

                <label for="productList">Product:</label>
                <select class="inputs" id="productList" name="productID">
                    <c:forEach var="product" items="${menu}">
                        <option value="${product.name}">${product.name}     €${product.price}</option>
                    </c:forEach>
                </select>
            

                <label for="quantity">Quantity:</label>
                <input class="inputs" type="number" id="quantity" name="quantity" value="1" min="1">

                <button type="button" onclick="addItem()">Add to Order</button>

            </form>
        </div>
        
        <div id="itemsContainer">
            <h3>Items added:</h3>
            <ul id="itemList">
                <c:forEach var="item" items="${order}">
                    <li> ${item.drinkName} x ${item.quantity} </li>
                </c:forEach>
            </ul>
        </div>

        <div>
            <a href="<c:url value="/home"/>">Back to Home</a>
        </div>

        <script type="text/javascript" src="<c:url value='/js/add-items.js'/>"></script>

    </body>
</html>