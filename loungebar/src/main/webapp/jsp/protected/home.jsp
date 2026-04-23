<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h1>Welcome to LoungeBar</h1>

<c:choose>
    <%-- Customer view --%>
    <c:when test="${role == 'CUSTOMER'}">
        <div class="menu-grid">
            <div class="card">
                <h3>Reserve a table</h3>
                <a href="${pageContext.request.contextPath}/jsp/create-reservation.jsp">
                    <button type="button">Go to Reservations</button>
                </a>
            </div>
            <div class="card">
                <h3>Leave a review</h3>
                <a href="${pageContext.request.contextPath}/jsp/create-review.jsp">
                    <button type="button">Go to Reviews</button>
                </a>
            </div>
            <div class="card"><h3>Check orders</h3><button>Click</button></div>
        </div>
    </c:when>

    <%-- Waiter view --%>
    <c:when test="${role == 'WAITER'}">
        <div class="menu-grid">
            <div class="card">
                <h3>Check reviews</h3>
                <form action="${pageContext.request.contextPath}/review/search-by-waiter" method="post">
                    <button type="submit">Click</button>
                </form>
            </div>
            
            <div class="card"><h3>List my orders</h3>
                <form action="${pageContext.request.contextPath}/order/search" method="post">
                    <button type="submit">Click</button>
                </form>
            </div>
            <div class="card">
                <h3>List supplies</h3>
                <a href="<c:url value="/jsp/protected/search-supply-form.jsp"/>">
                    <button>Click</button>
                </a>
            </div>
            

            <div class="card">
                <h3>View your shift</h3>
                <form action="${pageContext.request.contextPath}/shift/search-by-waiter" method="post">
                    <button type="submit">Click</button>
                </form>
            </div>
            <div class="card"><h3>Create an order</h3>
                <a href="<c:url value="/jsp/protected/create-order.jsp"/>">
                    <button>Click</button>
                </a>
            </div>

            <div class="card">
                <h3>Create shift</h3>
                <a href="<c:url value="/jsp/protected/create-shift-form.jsp"/>">
                    <button>Click</button>
                </a>
            </div>

            <br>

            <div>
                <a href="<c:url value="/logout"/>">
                    <button>Logout</button>
                </a>
            </div>
    </c:when>
</c:choose>