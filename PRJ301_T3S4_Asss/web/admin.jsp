
<%@page import="java.util.List"%>
<%@page import="sample.users.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !"AD".equals(loginUser.getRoleID())) {
                response.sendRedirect("login.html");
                return;
            }
            String search = (String) request.getParameter("search");
            if (search == null) {
                search = "";
            }
        %>

        Welcome: <%=loginUser.getFullName()%>
        <a href ="MainController?action=Logout">Logout</a>
        <form action="MainController" method = "POST">  
            <input type="submit" name="action" value="Logout"/>
        </form>
        <form action="MainController">  
            Search<input type="text" name="search" value="<%=search%>">
            <input type="submit" name="action" value="Search">
        </form>

        <%
            List<UserDTO> list = (List<UserDTO>) request.getAttribute("LIST_USER");
            if (list != null) {
                if (list.size() > 0) {
        %>
        <table border="1" cellspacing="2">
            <thead>
                <tr>
                    <th>No</th>
                    <th>User ID</th>
                    <th>Full name</th>
                    <th>Role ID</th>
                    <th>Password</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    for (UserDTO user : list) {
                %>
                <tr>
                    <td><%=count++%></td>
                    <td><%= user.getUserID()%></td>
                    <td><%= user.getFullName()%></td>
                    <td><%= user.getRoleID()%></td>
                    <td><%= user.getPassword()%></td>
                    <td>
                        <a href="MainController?action=Delete&search=<%=search%>&userID=<%=user.getUserID()%>">Delete</a>
                    </td>
                </tr>
                <%
                    }
                %>

            </tbody>
        </table>
        <%
            String error = (String) request.getAttribute("ERROR");
            if (error == null) {
                error = "";
            }
        %>
        <%=error%>
        <%
                }
            }
        %>

    </body>
</html>
