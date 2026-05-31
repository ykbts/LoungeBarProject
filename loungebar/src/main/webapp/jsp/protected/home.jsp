<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LoungeBar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>

<body>
  <div class="page-wrapper">
    <h1>Welcome to LoungeBar</h1>

    <c:choose>

        <%-- Customer view --%>
        <c:when test="${role == 'CUSTOMER'}"></br>
            <p class="dashboard-subtitle">Customer dashboard</p>
            <div class="menu-grid">

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="3" y="4" width="18" height="18" rx="2"/>
                            <line x1="16" y1="2" x2="16" y2="6"/>
                            <line x1="8" y1="2" x2="8" y2="6"/>
                            <line x1="3" y1="10" x2="21" y2="10"/>
                            <line x1="8" y1="14" x2="16" y2="14"/>
                        </svg>
                    </div>
                    <h3>Reserve a table</h3>
                    <p class="card-desc">Book a spot for your next visit to LoungeBar</p>
                    <a href="${pageContext.request.contextPath}/jsp/protected/create-reservation.jsp">
                        <button type="button">Book now</button>
                    </a>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"/>
                        </svg>
                    </div>
                    <h3>Leave a review</h3>
                    <p class="card-desc">Share your experience and help us improve our service</p>
                    <a href="${pageContext.request.contextPath}/jsp/protected/create-review.jsp">
                        <button type="button">Write a review</button>
                    </a>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </div>
                    <h3>My orders</h3>
                    <p class="card-desc">Track your current and past orders at the bar</p>
                    <a href="${pageContext.request.contextPath}/order/myOrder">
                        <button type="button">View orders</button>
                    </a>
                </div>

            </div>
            <div class="logout-wrap">
                <a href="<c:url value='/logout'/>"><button>&#8592; Logout</button></a>
            </div>
        </c:when>

        <%-- Waiter view --%>
        <c:when test="${role == 'WAITER'}"></br>
            <p class="dashboard-subtitle">Waiter dashboard</p>
            <div class="menu-grid">

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <polygon points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"/>
                        </svg>
                    </div>
                    <h3>Check reviews</h3>
                    <p class="card-desc">See feedback left by customers about your service</p>
                    <form action="${pageContext.request.contextPath}/review/search-by-waiter" method="post">
                        <button type="submit">View reviews</button>
                    </form>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="9" y="2" width="6" height="4" rx="1"/><path d="M5 4h-1a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6a2 2 0 0 0-2-2h-1"/>
                            <line x1="9" y1="12" x2="15" y2="12"/><line x1="9" y1="16" x2="13" y2="16"/>
                        </svg>
                    </div>
                    <h3>List orders</h3>
                    <p class="card-desc">Browse and manage all active and past orders</p>
                    <form action="${pageContext.request.contextPath}/order/search" method="post">
                        <button type="submit">View orders</button>
                    </form>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                        </svg>
                    </div>
                    <h3>List supplies</h3>
                    <p class="card-desc">Track available stock and inventory items</p>
                    <a href="<c:url value='/supply/search'/>">
                        <button type="button">View supplies</button>
                    </a>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <rect x="3" y="4" width="18" height="18" rx="2"/><line x1="16" y1="2" x2="16" y2="6"/><line x1="8" y1="2" x2="8" y2="6"/><line x1="3" y1="10" x2="21" y2="10"/>
                        </svg>
                    </div>
                    <h3>View your shift</h3>
                    <p class="card-desc">Check your upcoming and current schedule</p>
                    <form action="${pageContext.request.contextPath}/shift/search-by-waiter" method="post">
                        <button type="submit">View shift</button>
                    </form>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </div>
                    <h3>Create an order</h3>
                    <p class="card-desc">Start a new order for a table</p>
                    <a href="<c:url value='/jsp/protected/create-order.jsp'/>">
                        <button type="button">New order</button>
                    </a>
                </div>

                <div class="card">
                    <div class="card-icon">
                        <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round">
                            <circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/>
                        </svg>
                    </div>
                    <h3>Create shift</h3>
                    <p class="card-desc">Schedule a new shift for yourself</p>
                    <a href="<c:url value='/jsp/protected/create-shift-form.jsp'/>">
                        <button type="button">New shift</button>
                    </a>
                </div>

            </div>
            <div class="logout-wrap">
                <a href="<c:url value='/logout'/>"><button>&#8592; Logout</button></a>
            </div>
        </c:when>

    </c:choose>
  </div>
</body>
</html>