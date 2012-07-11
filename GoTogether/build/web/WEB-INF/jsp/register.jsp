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
      <div class="main"> 
          <ul class="menu">
            <li><a href="index.htm">Home</a></li>
        </ul>
          <h1 class="table_title">Registration:</h1>
          <form:form commandName="register" method="POST" name="register">
              <table>
                  <tr>
                      <td>Firstname: </td>
                      <td><form:input path="firstname"/></td>
                  </tr> 
                  <tr>
                      <td>Lastname: </td>
                      <td><form:input path="lastname"/></td>
                  </tr>
                  <tr>
                      <td>Username: </td>
                      <td><form:input path="username"/></td>
                  </tr>
                  <tr>
                      <td>Email: </td>
                      <td><form:input path="email"/></td>
                  </tr>
                  <tr>
                      <td>Password: </td>
                      <td><form:password path="password"/></td>
                  </tr>
                  <tr>
                      <td>Password: </td>
                      <td><form:password path="passwordcheck"/></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><input type="submit" value="Register" onclick="submitRegisterForm()"/></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><font color="red"><form:errors path="firstname"/></font></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><font color="red"><form:errors path="lastname"/></font></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><font color="red"><form:errors path="username"/></font></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><font color="red"><form:errors path="email"/></font></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><font color="red"><form:errors path="password"/></font></td>
                  </tr>
                  <tr>
                      <td></td>
                      <td><font color="red"><form:errors path="passwordcheck"/></font></td>
                  </tr>
              </table>                 
          </form:form>
    </div>
  </body>
</html>
