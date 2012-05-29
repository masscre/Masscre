<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
  <head>
      <title>GoTogether Registration</title>
      <link rel="stylesheet" href="css/default.css" type="text/css"/>  
      <link href="favicon.png" rel="icon" type="image/png" />
      <script language="javascript" src="js/sha256.js"></script>
       <script language="javascript" src="js/submitForm.js"></script>
  </head>
  <body>
      <div class="title"></div>           
      <div class="register_content">
          <div class="register_title"></div>
          <div class="devide"></div>
          <form:form commandName="register" method="POST" name="register">
              <p>
                <div class="register_label">Firstname: </div>
                <div class="register_input"><form:input path="firstname"/></div>
              </p>  
              <p>
                <div class="register_label">Lastname: </div>
                <div class="register_input"><form:input path="lastname"/></div>
              </p>
              <p>
                <div class="register_label">Username: </div>
                <div class="register_input"><form:input path="username"/></div>
              </p>
              <p>
                <div class="register_label">Password: </div>
                <div class="register_input"><form:password path="password"/></div>
              </p>
              <p>
                <div class="register_label">Password: </div>
                <div class="register_input"><form:password path="passwordcheck"/></div>
              </p>
              <p>                
              <div class="register_submit"><input type="submit" value="Register" onclick="submitRegisterForm()"/></div>
              </p>              
              <p>
                <font color="red">
                    <br><form:errors path="firstname"/></br>
                    <br><form:errors path="lastname"/></br>
                    <br><form:errors path="username"/></br>
                    <br><form:errors path="password"/></br>
                    <br><form:errors path="passwordcheck"/></br>
                </font> 
              </p>    
          </form:form>
      </div>
      <div class="join_us"></div> 
  </body>
</html>
