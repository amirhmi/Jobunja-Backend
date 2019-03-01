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
    <li>id : <c:out value="${user.id}"/></li>
    <li>firstName:
        <p style="direction: rtl; display: inline-block">
            <c:out value="${user.firstName}"/>
        </p>
    </li>
    <li>lastName:
        <p style="direction: rtl; display: inline-block">
            <c:out value="${user.lastName}"/>
        </p>
    </li>
    <li>jobTitle:
        <p style="direction: rtl; display: inline-block">
            <c:out value="${user.jobTitle}"/>
        </p>
    </li>
    <li>bio:
        <p style="direction: rtl; display: inline-block">
            <c:out value="${user.bio}"/>
        </p>
    </li>
    <li>skills:
        <ul>
            <c:forEach var="skill" items="${user.skills}">
                <li>
                    <c:out value="${skill.name}"/>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>
<c:if test = "${message != null && !message.isEmpty()}">
    <h4><c:out value = "${message}"/><h4>
</c:if>
</body>
<
