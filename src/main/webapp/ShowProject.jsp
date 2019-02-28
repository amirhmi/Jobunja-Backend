<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>
<ul>
    <li>id : <c:out value="${project.id}"/></li>
    <li>title:
        <p style="direction: rtl; display: inline-block">
            <c:out value="${project.title}"/>
        </p>
    </li>
    <li>description:
        <p style="direction: rtl; display: inline-block">
            <c:out value="${project.description}"/>
        </p>
    <li>imageUrl: <img src=<c:out value="${project.imageUrl}"/> style="width: 50px; height: 50px;"></li>
    <li>budget: <c:out value="${project.budget}"/></li>
</ul>
<form action="${pageContext.request.contextPath}/SetBid" method="POST">
    <label>Bid Amount:</label>
    <input type="number" name="bidAmount">
    <input type="hidden" name="id" value="${project.id}">
    <button>Submit</button>
</form>
</body>
</html>