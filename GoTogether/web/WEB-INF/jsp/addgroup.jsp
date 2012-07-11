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
        <div class="main">         
        <ul class="menu">
              <li><a href="main.htm">Home</a></li>
        </ul>
        <h1 class="table_title">Add group:</h1>
            <form:form commandName="addgroup" method="POST" name="addgroup">
                <table>
                    <tr>
                        <td>Groupname: </td>
                        <td><form:input type="text" path="groupName"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Add"/></td>
                    </tr>
                    <tr>
                        <td></font></td>
                        <td><font color="red"><form:errors path="groupName"/></td>
                    </tr>    
                </table> 
              </p>  
            </form:form>
        
        </div>    
    </body>
</html>
