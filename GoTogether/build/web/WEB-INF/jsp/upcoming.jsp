<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <li><a href="myrides.htm">My rides</a></li>
            <li><a href="#"><font color="white">Upcoming</font></a></li> 
            <li><a href="management.htm">Management</a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a>   
    </body>
    
    <h3>Upcoming events</h3>
    
    <div class="content">
            <table width="600px" border="1" bgcolor="64cc18">                
                <tr>
                    <td>Date</td>
                    <td>Time</td>
                    <td>From</td>
                    <td>To</td>
                    <td>Created by</td>
                    <td>Open</td>
                </tr>
                <c:forEach items="${rides}" var="current">
                    <tr>
                        <td><c:out value="${current.getDay()}" />/<c:out value="${current.getMonth()}" />/<c:out value="${current.getYear()}" /></td>
                        <td><c:out value="${current.getHourVisual()}" />:<c:out value="${current.getMinuteVisual()}" /></td>
                        <td><c:out value="${current.getFrom()}" /></td>    
                        <td><c:out value="${current.getTo()}" /></td> 
                        <td><c:out value="${current.getOwnerName()}" /></td>
                        <td><a href="rideedit.htm?id=${current.getId()}">OPEN</a></td>      
                    </tr>
                </c:forEach>                
            </table>
        </div>
</html>