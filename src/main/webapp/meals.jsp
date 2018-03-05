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
    </tr>

    <c:forEach items="${listMeals}" var="meal">
        <tr class=${meal.exceed ? 'exceed' : 'noExceed'}>
            <th>
                <javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
                <javatime:format value="${parsedDate}" pattern="dd-MM-yyyy HH:mm"/>
            </th>
            <th>${meal.description}</th>
            <th>${meal.calories}</th>
        </tr>

    </c:forEach>
</table>

</body>
</html>