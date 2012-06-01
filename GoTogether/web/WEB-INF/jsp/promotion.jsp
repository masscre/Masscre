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
        <div class="panel_corner"></div>
        <div class="panel">
            <div class="register"><a href="main.htm"><img src="img/home.png"/></a></div>
        </div>
        <div class="register_content">            
            <div class="devide"></div>
            <h1>Promote user ${username} to level</h1>
            <form:form commandName="promote" method="POST" name="promote">
                <p>
                    <div class="register_label">Level: </div>
                    <div class="register_input"><form:input type="text" path="level"/></div>
                </p>
                <p>                
                    <div class="register_submit"><input type="submit" value="Promote"/></div>
                </p> 
                <p>
                    <font color="red">
                        <br><form:errors path="level"/></br>                    
                    </font> 
                </p> 
            </form:form>
        </div>    

</body>
</html>