<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LoungeBar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create-shift-form.css">
</head>
<body>

<div class="page-wrapper page-wrapper--narrow">
    <h1>Create New Shift</h1>
    <p class="page-subtitle">Schedule a new work shift</p>

    <form action="${pageContext.request.contextPath}/shift/create" method="POST">
        <div class="form-group">
            <label for="startTimeInput">Start Date and Time:</label>
            <input type="datetime-local" name="start_time" id="startTimeInput" required>
        </div>

        <div class="form-group">
            <label for="endTimeInput">End Date and Time:</label>
            <input type="datetime-local" name="end_time" id="endTimeInput" required>
            <span class="form-hint">Shift duration must be between 1 and 8 hours</span>
        </div>

        <div class="duration-badge" id="durationBadge">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"
                 fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/>
            </svg>
            <span id="durationText">Duration: —</span>
        </div>

        <hr class="form-divider">

        <button type="submit">Create Shift</button>
    </form>

    <p><a href="<c:url value="/home"/>">Back to Home</a></p>
</div>

<script>
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
    document.getElementById('startTimeInput').value = now.toISOString().slice(0, 16);
</script>

</body>
</html>