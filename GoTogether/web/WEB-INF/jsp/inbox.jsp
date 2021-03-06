<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css" type="text/css"/>
        <link href="favicon.png" rel="icon" type="image/png" />
        <title>GoTogether - MESSENGER</title>
    </head>
    <body> 
        <div class="main">          
        <ul class="menu">
            <li><a href="main.htm">Home</a></li>  
            <li><a href="myrides.htm">My rides</a></li>
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>
        </ul>
            <div class="user_info">
                ${name}
            </div>  
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 
        
        <ul class="submenu">
            <li><a href="newmessagelist.htm">New message</a></li> 
            <li><a href="inbox.htm" class="active">Inbox</a></li> 
        </ul>
        
        <h1 class="table_title">Inbox:</h1>
        
            <table width="600px" border="1" bgcolor="0DD939">                
                <tr>
                    <td>From</td>
                    <td>Date</td>
                    <td>Subject</td>                     
                    <td>Open</td>
                    <td>Delete</td>
                </tr>
                <c:forEach items="${messages}" var="current">
                    <tr>
                        <td><c:out value="${current.getSenderInfo()}" /></td>                        
                        <td><c:out value="${current.getSendDate()}" /></td>
                        <td><c:out value="${current.getSubject()}" /></td>                        
                        <td><a href="readmessage.htm?id=${current.getId()}">OPEN</a></td>
                        <td><a href="deletemessage.htm?id=${current.getId()}">DELETE</a></td>
                    </tr>
                </c:forEach>                
            </table>        
        </div>
    </body>
</html>