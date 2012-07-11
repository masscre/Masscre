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
        <h1 class="table_title">Add ride:</h1>
            <form:form commandName="addride" method="POST" name="addride">
                <table>
                    <tr>
                        <td>Day:</td>
                        <td><form:input type="number" path="day"/></td>
                    </tr>
                    <tr>
                        <td>Month: </td>
                        <td><form:input type="number" path="month"/></td>
                    </tr>
                    <tr>
                        <td>Year: </td>
                        <td><form:input type="number" path="year" value="2012"/></td>
                    </tr>
                    <tr>
                        <td>Hour: </td>
                        <td><form:input type="number" path="hour"/></td>
                    </tr>
                    <tr>
                        <td>Minute: </td>
                        <td><form:input type="number" path="minute"/></td>
                    </tr>
                    <tr>
                        <td>From: </td>
                        <td><form:input type="text" path="from"/></td>
                    </tr>
                    <tr>
                        <td>To: </td>
                        <td><form:input type="text" path="to"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Add"/></td>
                    </tr>
                    
                    <tr>
                        <td></td>
                        <td><font color="red"><form:errors path="day"/></font></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><font color="red"><form:errors path="month"/></font></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><font color="red"><form:errors path="year"/></font></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><font color="red"><form:errors path="hour"/></font></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><font color="red"><form:errors path="minute"/></font></td>
                    </tr>
                    
                </table>                   
            </form:form>
        </div>   
    </body>
</html>
