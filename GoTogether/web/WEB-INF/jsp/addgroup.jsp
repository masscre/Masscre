<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoTogether</title>
        <link rel="stylesheet" href="css/default.css" type="text/css"/>  
        <link href="favicon.png" rel="icon" type="image/png" />
    </head>
    <body>
        <div class="panel_corner"></div>
        <div class="panel">
                <div class="register"><a href="groups.htm"><img src="img/home.png"/></a></div>
            </div>
        <div class="register_content">
            <div class="addride_title"></div>
            <div class="divide"></div>
            <form:form commandName="addgroup" method="POST" name="addgroup">
                <p>
                    <div class="register_label">Groupname: </div>
                    <div class="register_input"><form:input type="text" path="groupName"/></div>
                </p>                
                <p>                
                    <div class="register_submit"><input type="submit" value="Add"/></div>
                </p>  
                <p>
                <font color="red">
                    <br><form:errors path="groupName"/></br>                    
                </font> 
              </p>  
            </form:form>
        </div>       
    </body>
</html>
