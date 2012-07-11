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
       <div class="main">            
        <ul class="menu">
            <li><a href="main.htm">Home</a></li>  
            <li><a href="myrides.htm">My rides</a></li>
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="#" class="active">Management</a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>
        </ul>
            
        <div class="user_info">
                Logged as: ${name}
        </div>     
            
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a>  
        
        <ul class="submenu">
            <li><a href="groups.htm" class="active">Groups</a></li>
        </ul>        
        
        <h1 class="table_title">Groups:</h1>
        
        <a href="addgroup.htm">New group</a>
        
            <table>                 
                <tr>
                    <td>Group name</td>
                    <td>Edit</td>
                    <td>Delete</td>                    
                </tr>
                <c:forEach items="${groups}" var="current">
                    <tr>
                        <td><c:out value="${current.getGroupName()}" /></td>                        
                        <td><a href="groupedit.htm?id=${current.getId()}">EDIT</a></td>
                        <td><a href="groupremove.htm?id=${current.getId()}">DELETE</a></td>
                    </tr>
                </c:forEach>                
            </table>
        
       </div> 
    </body>
</html>
