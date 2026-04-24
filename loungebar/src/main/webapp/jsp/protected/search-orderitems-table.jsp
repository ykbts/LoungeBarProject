<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Client Order</title>
    </head>

    <body>
        <h1>Review my order</h1>
        <hr/>

        <form method="POST" action="<c:url value="/order/myOrder"/>">
            <h3>Insert the number of your table:</h3>
            <label for="table">Table Number:</label>
            <input id="table" name="table" type="text"/><br/><br/>

            <button type="submit">Search</button>
        </form>

        <hr/>


        <p><a href="<c:url value="/home"/>">Back to Home</a></p>
    </body>
</html>