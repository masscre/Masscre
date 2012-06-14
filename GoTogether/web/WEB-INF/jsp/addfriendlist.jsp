<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
      <title>GoTogether</title>
      <link rel="stylesheet" href="css/default.css" type="text/css"/>  
      <link href="favicon.png" rel="icon" type="image/png" /> 
      
  </head>
  <body>
      <div class="panel_corner2"></div>
          <div class="panel">
                <div class="register"><a href="main.htm">Home</a></div>                
          </div> 
          <div class="panel2">
                <div class="register"><a href="addfriend.htm">New search</a></div>
          </div>
      <div class="register_content">          
          <div class="divide"></div>
          <table width="400px" border="1" bgcolor="1FE812">
              <tr>
                  <td>Firstname</td>
                  <td>Lastname</td>
                  <td>Email</td>                  
                  <td>Add Friend</td>
              </tr>
              ${table}
          </table>                      
      </div>
      <div class="join_us"></div> 
  </body>
</html>

