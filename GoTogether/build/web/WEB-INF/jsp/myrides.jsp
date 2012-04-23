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
                  
        <ul>
            <li><a href="main.htm">Home</a></li>  
            <li><a href="#">My rides</a></li>
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        
        <div class="smallMenu"> 
            <a href="addride.htm">
                <img src="img/plus.png"/>
            </a>
        </div>       
        
        
        <div class="content">
            <table width="600px" border="1" bgcolor="0DD939">                
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
                        <td><a href="#">EDIT</a></td>      
                    </tr>
                </c:forEach>                
            </table>
        </div>
        
    </body>
</html>