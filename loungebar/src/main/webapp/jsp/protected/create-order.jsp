<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>LoungeBar - Create Order Form</title>

        <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
        <link rel="stylesheet" href="<c:url value='/css/components.css'/>">
        <link rel="stylesheet" href="<c:url value='/css/create-order.css'/>">
    </head>

    <body>
        <div class="page-wrapper">
            <h1>Create An Order</h1>
            <p class="page-subtitle">Open a new order for a table</p>

            <div class="order-layout">

                <form class="form-panel" method="POST" action="<c:url value="/order/create"/>">

                    <div class="form-group">
                        <label for="waiterInput">Waiter ID:</label>
                        <input type="text" name="waiterID" id="waiterInput"
                            value="<%= (session.getAttribute("user") != null) ? session.getAttribute("user") : "ERROR: There is no session" %>" readonly>
                    </div>

                    <div class="form-group">
                        <label for="tableInput">Table Number:</label>
                        <input type="number" name="tableID" id="tableInput">
                    </div>

                    <input type="hidden" name="dateTime" id="dateTimeInput">

                    <input type="hidden" name="status" id="statusInput" value="PAID">

                    <input type="hidden" name="payMethod" value="NONE">

                    <hr class="form-divider">

                    <button type="submit">Open Order</button>

                    <div id="message"></div>
                </form>

                <aside class="tables-panel">
                    <h2 class="tables-title">Tables</h2>
                    <div id="tablesList"></div>
                </aside>

            </div>
            </div>
            <p>
                <a class="back-link" href="<c:url value="/home"/>">Back to Home</a>
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


   