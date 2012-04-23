<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css" type="text/css"/>
        <link href="favicon.png" rel="icon" type="image/png" />
        <title>GoTogether</title>
        
        <script language="JavaScript">
            function toggle(id) {
                var state = document.getElementById(id).style.display;
                    if (state == 'block') {
                        document.getElementById(id).style.display = 'none';
                    } else {
                        document.getElementById(id).style.display = 'block';
                    }
                }
        </script>
        
    </head>
    <body> 
                  
        <ul>
            <li><a href="main.htm">Home</a></li>  
            <li><a href="#">My rides</a></li>
            <li><a href="upcoming.htm">Upcoming</a></li> 
            <li><a href="management.htm">Management</a></li> 
            <li><a href="logout.htm">Logout</a></li>
        </ul>
        
        <div class="smallMenu"> 
            <a href="addride.htm">
                <img src="img/plus.jpg"/>
            </a>
        </div>       
        
        <div class="content">
            <table width="600px" border="1" bgcolor="0DD939">
                <tr>
                    <td>Date</td>
                    <td>Time</td>
                    <td>From</td>
                    <td>To</td>
                    <td>Edit</td>
                </tr>
                <tr>
                    <td>31.5.2012</td>
                    <td>12:30</td>
                    <td>Příbram</td>
                    <td>Praha</td>
                    <td><a href="#">EDIT</a></td>
                </tr>
            </table>
        </div>
        
    </body>
</html>