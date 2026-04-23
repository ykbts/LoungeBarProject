<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>LoungeBar - Supply List</title>
    </head>

    <body>
        <h1>Supply Inventory</h1>
        <hr/>

        

        <c:if test='${not empty supplyList && !message.error}'>
            <table border="1" cellpadding="5">
                <thead>
                    <tr style="background-color: #eee;">
                        <th>Name</th>
                        <th>Units Available</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="supply" items="${supplyList}">
                        <tr>
                            <td><b><c:out value="${supply.name}"/></b></td>
                            <td><c:out value="${supply.unitsAvailable}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p>Total items found: ${supplyList.size()}</p>
        </c:if>

        <c:if test='${empty supplyList && !message.error}'>
            <p>The inventory is empty or no supplies match your filters.</p>
        </c:if>

        <hr/>
        <p><a href="<c:url value="/jsp/protected/search-supply-form.jsp"/>">Back to Search/List</a></p>
        <p><a href="<c:url value="/home"/>">Back to Home</a></p>
    </body>
</html>