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
        <div class="panel_corner"></div>
        <div class="panel">
                <div class="register"><a href="main.htm"><img src="img/home.png"/></a></div>
            </div>
        <div class="register_content">
            <div class="addride_title"></div>
            <div class="devide"></div>
            <form:form commandName="addride" method="POST" name="addride">
                <p>
                    <div class="register_label">Day: </div>
                    <div class="register_input"><form:input type="number" path="day"/></div>
                </p>  
                <p>
                    <div class="register_label">Month: </div>
                    <div class="register_input"><form:input type="number" path="month"/></div>
                </p>  
                <p>
                    <div class="register_label">Year: </div>
                    <div class="register_input"><form:input type="number" path="year" value="2012"/></div>
                </p>
                <p>
                    <div class="register_label">Hour: </div>
                    <div class="register_input"><form:input type="number" path="hour"/></div>
                </p>
                <p>
                    <div class="register_label">Minute: </div>
                    <div class="register_input"><form:input type="number" path="minute"/></div>
                </p>
                <p>
                    <div class="register_label">From: </div>
                    <div class="register_input"><form:input type="text" path="from"/></div>
                </p>
                <p>
                    <div class="register_label">To: </div>
                    <div class="register_input"><form:input type="text" path="to"/></div>
                </p>
                <p>                
                    <div class="register_submit"><input type="submit" value="Add"/></div>
                </p>  
                <p>
                <font color="red">
                    <br><form:errors path="day"/></br>
                    <br><form:errors path="month"/></br>
                    <br><form:errors path="year"/></br>
                    <br><form:errors path="hour"/></br>
                    <br><form:errors path="minute"/></br>
                </font> 
              </p>  
            </form:form>
        </div>       
    </body>
</html>
