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
           value="<%= (session.getAttribute("user") != null) ? session.getAttribute("user") : "ERROR: There is no session" %>" 
           readonly ">
</div>

    <div class="form-group">
        <label>Table Number:</label>
        <input type="number" name="tableID" placeholder="Ej: 5" required>
    </div>

    <div class="form-group">
        <label>Date and Time:</label>
        <input type="datetime-local" name="dateTime" id="dateTimeInput" required>
    </div>

    <div class="form-group">
        <label>Status:</label>
        <select name="status">
            <option value="PENDING">PENDING</option>
            <option value="PREPARING">PREPARING</option>
        </select>
    </div>

    <input type="hidden" name="payMethod" value="NONE">

    <button type="submit">Open Order</button>
</form>

<br/>
<p><a href="<c:url value="/home"/>">Back to Home</a></p>

<script>
    document.getElementById('dateTimeInput').value = new Date().toISOString().slice(0, 16);
</script>

   <a href="${pageContext.request.contextPath}/home">Back to Home</a>