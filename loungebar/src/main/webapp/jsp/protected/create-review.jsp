<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Create Reviews</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value="../../css/base.css"/>">
        <link rel="stylesheet" href="<c:url value="../../css/components.css"/>">
        <link rel="stylesheet" href="<c:url value="../../css/create-review.css"/>">

    </head>

  <body>

      <div class="page-wrapper page-wrapper--narrow">
        <h1>Reviews</h1>
        <br>

        <p class="page-subtitle">Select the waiter and leave a SCORE and, if you prefer, a Comment.</p>

        <div class="form-panel">
            <div class="form-group">
                <label for="waiterID">Waiter</label>
                <select id="waiterID">
                    <option value="">-- Select a waiter --</option>
                </select>
            </div>

            <div class="form-group">
                <label for="clientID">Customer username:</label>
                <input type="text" id="clientID" name="customerUsername"
                       value="<%= (session.getAttribute("user") != null) ? session.getAttribute("user") : "ERROR: There is no session" %>"
                       readonly>
            </div>

            <div class="form-group">
                <label for="score">Experience evaluation</label>
                <div class="star-rating" id="stars" role="radiogroup">
                    <input type="radio" name="rating" id="s1" value="1">
                    <label for="s1">★</label>
                    <input type="radio" name="rating" id="s2" value="2">
                    <label for="s2">★</label>
                    <input type="radio" name="rating" id="s3" value="3">
                    <label for="s3">★</label>
                    <input type="radio" name="rating" id="s4" value="4">
                    <label for="s4">★</label>
                    <input type="radio" name="rating" id="s5" value="5">
                    <label for="s5">★</label>
                </div>
                <input type="hidden" id="score" min="1" max="5">
            </div>

            <div class="form-group">
                <label for="comment">Comment</label>
                <textarea id="comment"></textarea>
            </div>

            <button class="btn-primary" type="button" id="confirmReview" onclick="createReview()">
                Send
            </button>

        </div>

        <div class="result-card" id="result">
            <div class="review-header" id="resultHeader"></div>
            <div class="review-body" id="resultBody"></div>
        </div>
      </div>

    <br/>
    <p><a class="back-link" href="<c:url value="/home"/>">Back to Home</a></p>


    <script>
        const contextPath = "<%= request.getContextPath() %>";
    </script>
    <script type="text/javascript" src="<c:url value="/js/review.js"/>"></script>

  </body>
</html>

