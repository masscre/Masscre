<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            <li><a href="administration.htm"><u><font color="white">Administration</font></u></a></li> 
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        <a href="#" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 
        <div class="smallMenu">             
        </div>       
        
        <div class="content">
            <table width="600px" border="1" bgcolor="0DD939">                
                <tr>
                    <td>Username</td>
                    <td>Lastname</td>
                    <td>Firstname</td>
                    <td>Promote</td>
                    <td>Delete</td>
                </tr>
                <c:forEach items="${users}" var="current">
                    <tr>
                        <td><c:out value="${current.getUsername()}" /></td>
                        <td><c:out value="${current.getLastname()}" /></td>
                        <td><c:out value="${current.getFirstname()}" /></td>    
                        <td><c:out value="${current.getRights()}" /></td> 
                        <td><a href="deleteuser.htm?id=${current.getId().toString()}">DELETE</a></td>      
                    </tr>
                </c:forEach>                
            </table>
        </div>
        
    </body>
</html>