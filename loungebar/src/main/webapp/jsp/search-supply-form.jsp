<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Search & List Supplies</title>
    </head>

    <body>
        <h1>Search & List Supplies</h1>
        <hr/>

        <form method="POST" action="<c:url value="/supply/search"/>">
            <h3>Search by Supply Name:</h3>
            <label for="nameID">Supply Name:</label>
            <input id="nameID" name="name" type="text" placeholder="Enter supply name"/><br/><br/>

            <button type="submit">Search</button>
            <button type="reset">Reset</button>
        </form>

        <hr/>

        <form method="POST" action="<c:url value="/supply/search"/>">
            <input type="hidden" name="listAll" value="true"/>
            <button type="submit">List All Supplies</button>
        </form>

        <hr/>
        <p><a href="<c:url value="/index.jsp"/>">Back to Home</a></p>
    </body>
</html>