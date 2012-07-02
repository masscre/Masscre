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
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="#"><font color="white">Management</font></a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a>  
        <div class="divide_menu">
            &#32;
        </div>

        <ul>
            <li><a href="management.htm"><i>Account</i></a></li> 
            <li><a href="groups.htm"><i><font color="white">Groups</font></i></a></li>               
        </ul>   
        
        <a href="addgroup.htm">Add group</a>
        
        <div class="content">
            <table width="600px" border="1" bgcolor="0DD939">                
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
