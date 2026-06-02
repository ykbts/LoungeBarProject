<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>LoungeBar - Search & List Supplies</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
    <link rel="stylesheet" href="<c:url value='/css/search-supply-form.css'/>">
</head>
<body>
<div class="page-wrapper" style="max-width: 1100px;">
    <h2 class="page-heading">Search &amp; List Supplies</h2>

    <div class="supply-grid">

        <div class="col-left">
            <div class="form-panel">
                <form method="POST" action="<c:url value="/supply/search"/>">
                    <div class="form-group">
                        <label for="nameSearch">Supply Name</label>
                        <input id="nameSearch" name="name" type="text"
                               placeholder="Enter supply name" class="form-control"/>
                    </div>
                    <button type="submit">Search</button>
                </form>
            </div>

            <div class="form-panel">
                <form method="POST" action="<c:url value="/supply/search"/>">
                    <input type="hidden" name="listAll" value="true"/>
                    <div class="list-all-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24"
                             fill="none" stroke="var(--navy-600)" stroke-width="1.5"
                             stroke-linecap="round" stroke-linejoin="round">
                            <path d="M9 5H7a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2h-2"/>
                            <rect x="9" y="3" width="6" height="4" rx="1"/>
                            <line x1="9" y1="12" x2="15" y2="12"/>
                            <line x1="9" y1="16" x2="13" y2="16"/>
                        </svg>
                        <p class="list-all-title">
                            Browse all inventory<br/>
                            <span class="list-all-subtitle">View the complete list of supplies currently in stock</span>
                        </p>
                    </div>
                    <button type="submit">&#9776; List All Supplies</button>
                </form>
            </div>
        </div>

        <div class="form-panel">
            <h2 class="page-heading" style="margin-bottom: 1.4rem;">Create New Supply</h2>
            <form method="POST" action="<c:url value="/supply/create"/>">
                <div class="form-group">
                    <label for="nameID">Supply Name</label>
                    <input id="nameID" name="name" type="text" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="unitsID">Units Available</label>
                    <input id="unitsID" name="units" type="text" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="typeID">Type</label>
                    <select id="typeID" name="type" class="form-control">
                        <option value="">Select a type</option>
                        <c:forEach var="type" items="${supplyTypes}">
                            <option value="${type}">${type}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit">Submit</button>
            </form>
        </div>

    </div>

    <p><a href="<c:url value="/home"/>"> Back to Home</a></p>
</div>

</div>
</body>
</html>