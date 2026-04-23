<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Reviews Page</title>

	</head>

  <body>
	<h1>Reviews</h1>
	<br>

	<div>
		<div class="field">
			<label for="waiterID">Waiter</label>
			<input type="text" id="waiterID">
		</div>
		<div class="field">
			<label for="clientID">Customer</label>
			<input type="text" id="clientID">
		</div>
		<div class="field">
			<label for="score">Experience evaluation</label>
			<input type="number" id="score" min="1" max="5">
		</div>
		<div class="field">
			<label for="comment">Comment</label>
			<input type="text" id="comment">
		</div>

		<button type="button" id="confirmReview" onclick="createReview()">
    	    Send
		</button>

	</div>

	<div id="result" style="margin: 2em;">
		<div id="resultHeader"></div>
        <div id="resultBody"></div>
	</div>

	<script type="text/javascript" src="<c:url value="/js/review.js"/>"></script>

    <a href="${pageContext.request.contextPath}/home">Back to Home</a>
	</body>
</html>