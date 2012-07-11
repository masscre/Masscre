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
        <div class="main">
            <ul class="menu">
                <li><a href="main.htm">Home</a></li>            
            </ul> 
            <h1 class="table_title">Do you really remove group ${groupname} ?</h1>
            <ul class="submenu">
                <li><a href="groupremoveok.htm?id=${deleteId}">Yes</a></li>  
                <li><a href="groups.htm">No</a></li>
            </ul>
        </div>    
        
    </body>
</html>