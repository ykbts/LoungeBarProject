<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Client Order</title>

        <!-- Font -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=EB+Garamond:ital,wght@0,400;0,600;1,400&family=Source+Sans+3:wght@400;500;600&display=swap">
        <!-- Style -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <link rel="stylesheet" href="<c:url value='/css/base.css'/>">
        <link rel="stylesheet" href="<c:url value='/css/search-orderitems-table.css'/>">
        <!-- <link rel="stylesheet" href="../../css/base.css">
        <link rel="stylesheet" href="../../css/search-orderitems-table.css"> -->

    </head>

    <body>
        <div class="page-wrapper">
            
            <div class="title">
                <h1>Review my order</h1>                
            </div>
            <hr/>
    
            <div class="order-form">
                <form class="form-text" method="POST" action="<c:url value="/order/myOrder"/>">
                    <h3>Insert the number of your table:</h3>
                    
                    <label for="table">Table Number:</label>
                    <div class="input-group mb-3">
                        <input class="form-control" id="table" name="table" type="text"/>
                        <button class="btn btn-outline-secondary" type="submit">Search</button>
                    </div>
                        
                </form>
            </div>
    
    
            <p>
                <a class="back-link" href="<c:url value="/home"/>">Back to Home</a>
            </p>
            
        </div>

    </body>
</html>