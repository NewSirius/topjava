<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>

    <style>
        .exceed {
            width: 100%;
            text-align: left;
            color: red;
        }

        .noExceed {
            width: 100%;
            text-align: left;
            color: green;
        }
    </style>

</head>
<body>

<h3><a href="index.html">Home</a></h3>

<table border="1" cellpadding="10">
    <tr>
        <td>Date/Time</td>
        <td>Description</td>
        <td>Calories</td>
        <td></td>
        <td></td>
    </tr>

    <c:forEach items="${listMeals}" var="meal">
        <tr class=${meal.exceed ? 'exceed' : 'noExceed'}>
            <td>
                <javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
                <javatime:format value="${parsedDate}" pattern="dd-MM-yyyy HH:mm"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">Edit</a></td>
            <td><a href="meals?action=remove&id=${meal.id}">Remove</a></td>
        </tr>
    </c:forEach>
</table>

<br>
<br>

<form method="POST" action='meals' name="AddMeal">
    <input type = "hidden" name = "id" value = "${meal.id}" >
    Date/Time:<br>
    <input type="datetime-local" value="${meal.dateTime}" name="date"><br>
    Description:<br>
    <input type="text" value="${meal.description}" name="description"><br>
    Calories:<br>
    <input type="number" value="${meal.calories}" min="0" name="calories"><br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>