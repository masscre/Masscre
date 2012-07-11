﻿﻿<%@page contentType="text/html" pageEncoding="UTF-16"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
      <title>GoTogether</title>
      <link rel="stylesheet" href="css/default.css" type="text/css"/>  
      <link href="favicon.png" rel="icon" type="image/png" /> 
      
  </head>
  <body>
      <div class="main"> 
          <ul class="menu">
              <li><a href="main.htm">Home</a></li>
          </ul>    
          <h1 class="table_title">Add friend:</h1>
            <form:form commandName="addfriend" method="POST" name="addfriend">
                <table>
                    <tr>
                        <td><div class="register_label">Firstname: </div></td>
                        <td><div class="register_input"><form:input path="firstName"/></div></td>
                    </tr>
                    <tr>
                        <td><div class="register_label">Lastname: </div></td>
                        <td><div class="register_input"><form:input path="lastName"/></div></div></td>
                    </tr>
                    <tr>
                        <td><div class="register_label">Email: </div></td>
                        <td><div class="register_input"><form:input path="email"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="Search"/></td>                        
                    </tr>
                </table>                                    
            </form:form>

        
        <div class="join_us"></div> 
      </div>  
  </body>
</html>

