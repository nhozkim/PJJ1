
<%@page import="sample.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <%
         UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
         if(loginUser == null) loginUser = new UserDTO();
        %>
        User's information
        User ID: <%= loginUser.getUserID()%>
        <br>Full name: <%= loginUser.getFullName()%>
        <br>Role ID <%= loginUser.getRoleID()%>
        <br>Password: <%= loginUser.getPassword()%>
    </body>
</html>
