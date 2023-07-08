
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Input user's information
        <form action="MainController" method="POST">
            User ID <input type="text" name="userID" required=""/></br>
            Password <input type="password" name="password" required=""/></br>
            <input type="submit" name="action" value="Login"/>
            <input type="reset" value="Reset"/>
        </form>
        <a href ="MainController?action=CreatePage">Create Page</a>
        </br><a href ="MainController?action=shoppingPage">Phuc An's Mall</a>

        <%
            String error = request.getParameter("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%= error%>
    </body>
</html>
