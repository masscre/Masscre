<%@page contentType="text/html" pageEncoding="UTF-16"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
      <title>GoTogether</title>
      <link rel="stylesheet" href="css/default.css" type="text/css"/>  
      <link href="favicon.png" rel="icon" type="image/png" /> 
      
  </head>
  <body>
      <div class="panel_corner"></div>
        <div class="panel">
                <div class="register"><a href="main.htm"><img src="img/home.png"/></a></div>
            </div>         
      <div class="register_content">          
          <div class="divide"></div>
          <form:form commandName="addfriend" method="POST" name="addfriend">
              <p>
                <div class="register_label">Firstname: </div>
                <div class="register_input"><form:input path="firstName"/></div>
              </p>  
              <p>
                <div class="register_label">Lastname: </div>
                <div class="register_input"><form:input path="lastName"/></div>
              </p>                
              <p>
                <div class="register_label">Email: </div>
                <div class="register_input"><form:input path="email"/></div>
              </p> 
              <p>                
              <div class="register_submit"><input type="submit" value="Search"/></div>
              </p>                  
          </form:form>
                      
      </div>
      <div class="join_us"></div> 
  </body>
</html>

