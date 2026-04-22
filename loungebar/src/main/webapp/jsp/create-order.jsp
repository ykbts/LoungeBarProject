<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/order/create" method="POST">
    
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

<script>
    
    document.getElementById('dateTimeInput').value = new Date().toISOString().slice(0, 16);
</script>