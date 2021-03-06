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
        <div class="main">
        <ul class="menu">
            <li><a href="main.htm">Home</a></li>  
            <li><a href="myrides.htm">My rides</a></li>
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            <li><a href="administration.htm" class="active">Administration</a></li> 
            <li><a href="logout.htm">Logout</a></li>
        </ul>
            <div class="user_info">
                ${name}
        </div> 
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 
        <div class="divide_menu">
            &#32;
        </div>    
            <ul class="submenu">
                <li><a href="#" class="active">Users</a></li>                  
            </ul>
           
            <h1 class="table_title">Users: </h1>
            <table width="600px" border="1" bgcolor="0DD939">                
                <tr>
                    <td>Username</td>
                    <td>Lastname</td>
                    <td>Firstname</td>
                    <td>User level</td>
                    <td>Delete</td>
                </tr>
                <c:forEach items="${users}" var="current">
                    <tr>
                        <td><c:out value="${current.getUsername()}" /></td>
                        <td><c:out value="${current.getLastname()}" /></td>
                        <td><c:out value="${current.getFirstname()}" /></td>    
                        <td><a href="promotion.htm?id=${current.getId().toString()}"><c:out value="${current.getRights()}" /></a></td> 
                        <td><a href="removeuser.htm?id=${current.getId().toString()}">DELETE</a></td>      
                    </tr>
                </c:forEach>                
            </table>
        </div>
        
    </body>
</html>