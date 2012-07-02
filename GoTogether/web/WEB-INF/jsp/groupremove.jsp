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
            <div class="dIvide"></div>
            <h1>Do you really remove group ${groupname} ?</h1>
            <div class="register_submit"><a href="groupremoveok.htm?id=${deleteId}">Yes</div>
        </div>    
        
    </body>
</html>