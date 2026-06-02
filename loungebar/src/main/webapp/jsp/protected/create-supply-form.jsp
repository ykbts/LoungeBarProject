<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>LoungeBar - Create Supply Form</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create-supply-form.css">

    </head>

    <body>
        <a href="<c:url value="/home"/>" class="fixed-home-btn">Home</a>

        <div class="container">
            <h1>Create Supply Form</h1>

            <form method="POST" action="<c:url value="/supply/create"/>">
                <label for="nameID">Supply Name:</label>
                <input id="nameID" name="name" type="text" required/>

                <label for="unitsID" style="margin-top: 1.2rem;">Units Available:</label>
                <input id="unitsID" name="units" type="text" required/>

                <button type="submit">Submit</button>
                <button type="reset">Reset the form</button>
            </form>
        </div>
    </body>
</html>