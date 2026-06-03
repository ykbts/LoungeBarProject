<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>LoungeBar - Create Supply Result</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/components.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/create-supply-result.css">
    </head>

    <body>

    <a href="<c:url value="/home"/>" class="fixed-home-btn">Home</a>

    <div class="page-wrapper page-wrapper--narrow">
        <h1>Create Supply Result</h1>
        <p class="page-subtitle">Supply creation result</p>

        <c:choose>


        <c:when test="${not empty supply && !message.error}">
                    <div class="result-card success">
                        <div class="result-header">
                            <div class="result-icon">
                                <svg viewBox="0 0 24 24"><polyline points="20 6 9 17 4 12"/></svg>
                            </div>
                            <div>
                                <p class="result-title">Supply created successfully</p>
                                <p class="result-message"><c:out value="${message.message}"/></p>
                            </div>
                        </div>

                        <div class="supply-details">
                            <table>
                                <tr>
                                    <td>Supply Name</td>
                                    <td><c:out value="${supply.name}"/></td>
                                </tr>
                                <tr>
                                    <td>Units Available</td>
                                    <td><span class="supply-id-badge"><c:out value="${supply.unitsAvailable}"/></span></td>
                                </tr>
                            </table>
                        </div>

                        <div class="result-actions">
                            <a href="${pageContext.request.contextPath}/supply/create" class="btn-secondary">
                                Create Another Supply
                            </a>
                            <p><a href="<c:url value="/home"/>">Back to Home</a></p>
                        </div>
                    </div>
                </c:when>


                <c:otherwise>
                    <div class="result-card error">
                        <div class="result-header">
                            <div class="result-icon">
                                <svg viewBox="0 0 24 24"><line x1="18" y1="6" x2="6" y2="18"/><line x1="6" y1="6" x2="18" y2="18"/></svg>
                            </div>
                            <div>
                                <p class="result-title">Failed to create supply</p>
                                <p class="result-message"><c:out value="${message.message}"/></p>
                            </div>
                        </div>

                        <div class="result-actions">
                            <a href="${pageContext.request.contextPath}/supply/create" class="btn-primary">
                                Try Again
                            </a>
                            <p><a href="<c:url value="/home"/>">Back to Home</a></p>
                        </div>
                    </div>
                </c:otherwise>

            </c:choose>
        </div>

        </body>
        </html>