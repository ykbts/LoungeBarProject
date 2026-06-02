<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">

		<title>Create reservation</title>

      <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
      <link rel="stylesheet" href="<c:url value='/css/components.css'/>">
      <link rel="stylesheet" href="<c:url value='/css/create-reservation.css'/>">
      <!-- <link rel="stylesheet" href="../../css/base.css"/>
      <link rel="stylesheet" href="../../css/components.css"/>
      <link rel="stylesheet" href="../../css/create-reservation.css"/> -->

	</head>

  <body>

      <div class="page-wrapper page-wrapper--narrow">
      
         <h1>Create Reservation</h1>
         
         <p class="page-subtitle">Let us know WHEN you want the table to booked and the NUMBER of expected people.</p>

         <div class="form-panel">

            <div class="form-block">
               <div class="form-group">
                  <label for="startTime">Start Time</label>
                  <input type="datetime-local" id="startTime">
               </div>
               
               <div class="form-group">
                  <label for="endTime">End Time</label>
                  <input type="datetime-local" id="endTime">
               </div>
            </div>

            <div class="form-group">
               <label for="clientID">Customer username:</label>
               <input type="text" id="clientID" name="customerUsername"
                     value="<%= (session.getAttribute("user") != null) ? session.getAttribute("user") : "ERROR: There is no session" %>"
                     readonly>
            </div>

            <div class="form-block">
               <div class="form-group">
                  <label for="tableNumber">Table Number</label>
                  <input type="number" id="tableNumber" min="1">
               </div>
               
               <div class="form-group">
                  <label for="numberOfPeople">Guests</label>
                  <input type="number" id="numberOfPeople" min="1">
               </div>
            </div>
               
            <button class="btn-primary" type="button" id="confirmReservation" onclick="createReservation()">
               Confirm Reservation
            </button>
         </div>

         <div class="result-card" id="result">
            <div class="reservation-header" id="resultHeader"></div>
            <div class="reservation-body" id="resultBody"></div>
         </div>

      </div>

      <p>
         <a class="back-link" href="<c:url value="/home"/>">Back to Home</a>
      </p>

      <script type="text/javascript" src="<c:url value="/js/reservation.js"/>"></script>

	</body>
</html>