<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Create Order Form</title>
    </head>

    <body>
        <h1>Create Order Form</h1>
        <hr/>

        <form method="POST" action="<c:url value="/order/create"/>">
            
            <div class="form-group">
                <label>Waiter ID:</label>
                <input type="text" name="waiterID" 
                    value="<%= (session.getAttribute("user") != null) ? session.getAttribute("user") : "ERROR: There is no session" %>" readonly>
            </div>

            <div class="form-group">
                <label for="tableInput">Table Number:</label>
                <input type="number" name="tableID" id="tableInput" placeholder="Ej: 5">
            </div>

            <input type="hidden" name="dateTime" id="dateTimeInput">

            <input type="hidden" name="status" id="statusInput" value="PAID">

            <input type="hidden" name="payMethod" value="NONE">

            <button type="submit">Open Order</button>
        </form>

        <div id="message">

        </div>

        <div id="tablesList">

        </div>

        <br/>

        <p>
            <a href="<c:url value="/home"/>">Back to Home</a>
        </p>

        <script type="text/javascript" src="<c:url value="/js/tables.js"/>"></script>

        <script>
            const tableIDInput = document.getElementById('tableInput');
            const statusInput = document.getElementById('statusInput');

            tableIDInput.addEventListener('input', function() {
                statusInput.value = this.value.trim() !== '' ? 'PENDING' : 'PAID';
            });    

            document.getElementById('dateTimeInput').value = new Date().toISOString().slice(0, 16);

        </script>

    </body>
</html>


   