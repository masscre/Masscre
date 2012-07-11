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
            <li><a href="#">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 
        <div class="divide_menu">
            &#32;
        </div>
        <ul class="submenu">
            <li><a href="newmessage.htm" class="active">New message</a></li> 
            <li><a href="inbox.htm">Inbox</a></li> 
        </ul>
        
        <h1 class="table_title">New message:</h1>
        
            <table width="600px" border="1" bgcolor="0DD939">                
                <tr>
                    <td>Firstname</td>
                    <td>Lastname</td>
                    <td>Send message</td>                    
                </tr>
                <c:forEach items="${friends}" var="current">
                    <tr>
                        <td><c:out value="${current.getFirstname()}" /></td>
                        <td><c:out value="${current.getLastname()}" /></td>                        
                        <td><a href="newmessage.htm?id=${current.getId()}">send message</a></td>      
                    </tr>
                </c:forEach>                
            </table>
        </div>
        
    </body>
</html>
