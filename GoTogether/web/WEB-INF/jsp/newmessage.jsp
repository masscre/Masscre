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
        <ul class="menu"
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
          <form:form commandName="sendmessage" method="POST" name="sendmessage">
              <p>
                <div class="register_label">Subject: </div>
                <div class="register_input"><form:input path="subject"/></div>
              </p>  
              <p>
                <div class="register_label">Message: </div>
                <div class="register_input"><form:input path="message"/></div>
              </p>             
              <p>                
              <div class="register_submit"><input type="submit" value="Send"/></div>
              </p>                  
          </form:form>                      
       </div>
        
    </body>
</html>