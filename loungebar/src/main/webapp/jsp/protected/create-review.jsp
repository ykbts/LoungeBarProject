<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Reviews Page</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">

    </head>

  <body>
	<h1>Reviews</h1>
	<br>

    <div>
        <div class="field">
            <label for="waiterID">Waiter</label>
            <select id="waiterID">
                <option value="">-- Select a waiter --</option>
            </select>
        </div>

        <div class="field">
            <label for="clientID">Customer username:</label>
            <input type="text" id="clientID" name="customerUsername"
                   value="<%= (session.getAttribute("user") != null) ? session.getAttribute("user") : "ERROR: There is no session" %>"
                   readonly>
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

<br/>
<p><a href="<c:url value="/home"/>">Back to Home</a></p>

    <script>
        const contextPath = "<%= request.getContextPath() %>";
    </script>
    <script type="text/javascript" src="<c:url value="/js/review.js"/>"></script>

  </body>
</html>