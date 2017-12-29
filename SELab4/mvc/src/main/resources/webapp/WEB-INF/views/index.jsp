<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
    <title>Business Helper</title>
</head>
<body>

<c:forEach var="list" items="${lists}">
<h4>${list.getName()}</h4>
<table>
    <tr>
        <c:forEach var="business" items="${list.getBusinesses()}">
        <tr>
            <td>${business.getDescription()}</td>
        </tr>
        </c:forEach>
    </tr>
</table>
</c:forEach>

<h3>Business</h3>
<form:form modelAttribute="business" method="POST" action="/business">
    <table>
        <tr>
            <td><form:label path="description">Description:</form:label></td>
            <td><form:input path="description"/></td>
        </tr>
        <tr>
            <td><form:label path="listName">List name:</form:label></td>
            <td><form:input path="listName"/></td>
        </tr>
    </table>

    <input type="submit" class="button" name="add" value="Add">
    <input type="submit" class="button" name="delete" value="Delete">
</form:form>

<h3>List</h3>
<form method="POST" action="/list">
    First Name: <input type = "text" name = "name">

    <input type="submit" class="button" name="add" value="Add">
    <input type="submit" class="button" name="delete" value="Delete">
</form>
</body>
</html>
