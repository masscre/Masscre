<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <div class="user_info">
                ${name}
            </div>
        <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 
        <div class="divide_menu">
            &#32;
        </div>
        <ul class="submenu">
            <li><a href="newmessage.htm"><font color="white">New message</font></a></li> 
            <li><a href="inbox.htm">Inbox</a></li> 
        </ul>
        <h1 class="table_title">Message:</h1>
        <table>
            <tr>
                <td>Subject:</td>
                <td> Message:</td>                
            </tr>
            <tr>
                <td>${subject}</td>
                <td>${message}</td>
            </tr>            
        </table>
        
        <h1 class="table_title">Actions:</h1>    
            
        <ul class="submenu">
            <li><a href="newmessage.htm?id=${from}">Reply</a></li>    
            <li><a href="deletemessage.htm?id=${messageId}">Delete</a></li>
        </ul>
          
       </div>
        
    </body>
</html>