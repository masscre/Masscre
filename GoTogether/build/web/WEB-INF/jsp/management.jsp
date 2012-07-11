<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
             
            <div class="divide_menu">
                &#32;
            </div>
            
            <a href="inbox.htm" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a> 

            <ul class="submenu">
                <li><a href="groups.htm"><i>Groups</i></a></li>               
            </ul>   
        </div>
        
    </body>
</html>