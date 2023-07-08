<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Flower Page</title>
    </head>
    <body>
        <h1>Welcome to Cloud Flower Farm - CFF</h1>
        <form action ="MainController" method="POST">
            <select name="cmbFlower">
                <option value ="F01-Coin Flower-20">Coin Flower-20 $</option>
                <option value ="F02-Sun Flower-30">Sun Flower-30 $</option>
                <option value ="F03-Daisy Flower-15">Daisy Flower-15 $</option>
                <option value ="F04-Rose Flower-50">Rose Flower-50 $</option>
            </select>
            <select name="cmbQuantity">
                <option value ="1">1</option>
                <option value ="2">2</option>
                <option value ="3">3</option>
                <option value ="4">4</option>
                <option value ="5">5</option>
                <option value ="10">10</option>
            </select>
            <input type="submit" name="action" value="Add"/>
            <input type="submit" name="action" value="View"/>

        </form>
        <%
            String message = (String) request.getAttribute("MESSAGE");
            if (message == null) {
                message = "";
            }
        %>
        <%= message%>
    </body>
</html>