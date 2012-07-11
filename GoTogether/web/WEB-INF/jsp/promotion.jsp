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
            
            <h1 class="table_title">Promote user ${username} to level</h1>
            <form:form commandName="promote" method="POST" name="promote">
                <table>
                    <tr>
                        <td>Level: </td>
                        <td><form:input type="text" path="level"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Promote"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><font color="red"><form:errors path="level"/></font></td>
                    </tr>    
                </table> 
            </form:form>
        </div>    

</body>
</html>