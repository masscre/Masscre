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
                  
        <ul>
            <li><a href="main.htm">Home</a></li>  
            <li><a href="myrides.htm">My rides</a></li>
            <li><a href="#">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            ${adm}
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        <a href="#" style="position: absolute; top: 5px; right: 10px;"><img src="img/envelope.png"/></a>   
    </body>
</html>