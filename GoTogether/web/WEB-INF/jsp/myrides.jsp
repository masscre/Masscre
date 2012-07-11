<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css" type="text/css"/>
        <link href="favicon.png" rel="icon" type="image/png" />
        <title>GoTogether</title>     

    </head>
    <body> 
        <div class="main">          
            <ul class="menu">
                <li><a href="main.htm" >Home</a></li>  
                <li><a href="#" class="active">My rides</a></li>
                <li><a href="upcoming.htm">Upcoming</a></li> 
                <li><a href="management.htm">Management</a></li> 
                ${adm}
                <li><a href="logout.htm">Logout</a></li>                         
            </ul>
            <div class="user_info">
                Logged as: ${name}
            </div>    

            <div class="divide_menu">
                &#32;
            </div>

            <ul class="submenu">
                <li><a href="addride.htm"><i>Add ride</i></a></li>               
            </ul>      

            <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 

            <h1 class="table_title">My rides:</h1>
            <table>                
                <tr>
                    <td>Date</td>
                    <td>Time</td>
                    <td>From</td>
                    <td>To</td>
                    <td>Edit</td>
                </tr>
                <c:forEach items="${rides}" var="current">
                    <tr>
                        <td><c:out value="${current.getDay()}" />/<c:out value="${current.getMonth()}" />/<c:out value="${current.getYear()}" /></td>
                        <td><c:out value="${current.getHourVisual()}" />:<c:out value="${current.getMinuteVisual()}" /></td>
                        <td><c:out value="${current.getFrom()}" /></td>    
                        <td><c:out value="${current.getTo()}" /></td> 
                        <td><a href="rideedit.htm?id=${current.getId()}">EDIT</a></td>      
                    </tr>
                </c:forEach>                
            </table>

        </div>
    </body>
</html>