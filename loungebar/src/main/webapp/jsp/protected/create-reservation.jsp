<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Reservation Page</title>

	</head>

  <body>
	<h1>Reservations</h1>
	<br>

	<div>
		<div class="field">
			<label for="startTime">Start Time</label>
			<input type="datetime-local" id="startTime">
		</div>
		<div class="field">
			<label for="endTime">End Time</label>
			<input type="datetime-local" id="endTime">
		</div>
		 <div class="field">
            <label for="customerID">Client Username</label>
            <input type="text" id="customerID">
        </div>
		<div class="field">
			<label for="tableNumber">Table Number</label>
			<input type="number" id="tableNumber" min="1">
		</div>
		<div class="field">
			<label for="numberOfPeople">Guests</label>
			<input type="number" id="numberOfPeople" min="1">
		</div>
		
		<button type="button" id="confirmReservation" onclick="createReservation()">
    	Confirm Reservation
		</button>
		
	</div>

	<div id="result" style="margin: 2em;">
		<div id="resultHeader"></div>
        <div id="resultBody"></div>
	</div>

	<br/>
    <p><a href="<c:url value="/jsp/home"/>">Back to Home</a></p>

	<script type="text/javascript" src="<c:url value="/js/reservation.js"/>"></script>

	</body>
</html>