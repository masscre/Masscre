<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li><a href="#" class="active">Home</a></li>  
            <li><a href="myrides.htm">My rides</a></li>
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>                         
        </ul>
        <div class="user_info">
                ${name}
        </div>    

        <div class="divide_menu">
            &#32;
        </div>

        <ul class="submenu">
            <li><a href="addfriend.htm">Add friend</a></li>               
        </ul>    

        <a href="inbox.htm" class="btn_inbox"><img src="img/envelope.png"/></a>

        
        
        ${requests}
        ${requestsTable}

        <h1 class="table_title">Friends list:</h1>
        <table>
            <tr class="title">
                <td>Firstname</td>
                <td>Lastname</td>
                <td>Group</td>
                <td>Send mesage</td>
                <td>Delete</td>
            </tr>
            ${friendsList}
        </table>
        </div>
    </body>
</html>
