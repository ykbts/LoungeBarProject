<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Create Supply Form</title>
    </head>

    <body>
        <h1>Create Supply Form</h1>
        <hr/>

        <form method="POST" action="<c:url value="/supply/create"/>">
            <label for="nameID">Supply Name:</label>
            <input id="nameID" name="name" type="text" required/><br/>

            <label for="unitsID">Units Available:</label>
            <input id="unitsID" name="units" type="text" required/><br/><br/>

            <button type="submit">Submit</button><br/>
            <button type="reset">Reset the form</button>
        </form>

        <p><a href="<c:url value="/index.jsp"/>">Back to Home</a></p>
    </body>
</html>