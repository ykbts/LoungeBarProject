<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h2>Create New Shift</h2>

<form action="${pageContext.request.contextPath}/shift/create" method="POST">


    <div class="form-group">
        <label>Start Date and Time:</label>
        <input type="datetime-local" name="start_time" id="startTimeInput" required>
    </div>

    <div class="form-group">
        <label>End Date and Time:</label>
        <input type="datetime-local" name="end_time" id="endTimeInput" required>
    </div>

    <button type="submit">Create Shift</button>
</form>

<br/>
<p><a href="<c:url value="/home"/>">Back to Home</a></p>

<script>
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    document.getElementById('startTimeInput').value = now.toISOString().slice(0, 16);
</script>