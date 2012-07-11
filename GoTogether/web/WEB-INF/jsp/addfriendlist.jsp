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
      <div class="main"> 
          <ul class="menu">
              <li><a href="main.htm">Home</a></li>
          </ul>    
          
          <div class="divide_menu">
                &#32;
          </div>
          
          <ul class="submenu">
                <li><a href="main.htm">Home</a></li>     
                <li><a href="addfriend.htm">New search</a></li>
          </ul> 
          
          <h1 class="table_title">Search results:</h1>
      
          
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
      </div>
  </body>
</html>

