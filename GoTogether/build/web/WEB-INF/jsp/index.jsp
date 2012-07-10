<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/default.css" type="text/css"/>
        <link href="favicon.png" rel="icon" type="image/png" />
        <title>GoTogether</title>
        <script language="javascript" src="js/sha256.js"></script>
        <script language="javascript" src="js/submitForm.js"></script>
    </head>
    
    

    <body>
            
        <a class="btn_register" href="register.htm">REGISTER</a>
        
        <div class="main">  
            
            <h1 class="title">Go<br>Together</h1>            
            
            
                
                    <form:form class="form_login" commandName="login" method="POST" name="login">
                        <table>
                            <tr>
                                <td class="align_right">Username:</td> <td><form:input path="username"/></td>
                            </tr>
                            <tr>
                                <td class="align_right">Password:</td> <td><form:password path="password"/></td>
                            </tr>                             
                            <tr>
                                <td></td><td><input type="submit" value="Login" onClick="submitLoginForm()"></td>
                            </tr>
                            <tr>
                                <td></td><td><font color="red"><form:errors path="username"/></font></td>
                            </tr>
                            <tr>
                                <td></td><td><font color="red"><form:errors path="password"/></font></td>
                            </tr>                           
                        </table>
                    </form:form> 
            
        </div>
    </body>
</html>
