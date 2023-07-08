<%-- 
    Document   : viewCart
    Created on : Jun 17, 2023, 4:06:02 PM
    Author     : AN515-55
--%>

<%@page import="java.sql.Date"%>
<%@page import="sample.users.Order"%>
<%@page import="sample.users.Flower"%>
<%@page import="sample.users.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your cart</title>
    </head>
    <body>
        <h1>Let's enjoy our flower: </h1>
        <%
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
                if (cart.getCart().size() > 0) {
        %>
        <table border="1">
            <thead>
                <tr>
                    <th>No</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Edit</th>
                    <th>Remove</th>
                    <th>Total</th>
                </tr>
            </thead>
            <tbody>
                <%
                    int count = 1;
                    double total = 0;
                    for (Flower flower : cart.getCart().values()) {
                        total += flower.getQuantity() * flower.getPrice();
                %>
            <form action="MainController" method ="POST">
                <tr>
                    <td><%= count++%></td>
                    <td>
                        <input type="text" name="id" value= "<%= flower.getId()%>" readonly="" />
                    </td>

                    <td><%= flower.getName()%></td>
                    <td><%= flower.getPrice()%></td>
                    <td>
                        <input type="number" name="quantity" value= "<%= flower.getQuantity()%>" min="1" required="" />
                    </td>
                    <td>
                        <input type="submit" name="action" value= "Edit" />
                    </td>
                    <td>
                        <input type="submit" name="action" value= "Remove" />
                    </td>
                    <td>
                        <%= flower.getQuantity() * flower.getPrice()%>
                    </td>

                </tr>
            </form>

            <%
                }
            %>
        </tbody>
    </table>
    <h1>Total: <%= total%>$</h1>                
    <%                }
        }
    %>
    <a href ="shopping.html">Add more</a>
    <form action="CheckoutController" method="POST">
        <input type="submit" value="Checkout">
    </form> 

    <%
        Order currentOrder = (Order) session.getAttribute("CURRENT_ORDER");
        int orderIDValue = currentOrder.getOrderID();
        Date orderDate = currentOrder.getDate();
        double total_Value = currentOrder.getTotal();
    %>

    <form action="LoginController" method="POST">
        <input type="hidden" name="action" value="Checkout">
        <input type="hidden" name="orderID" value="<%= orderIDValue%>">
        <input type="hidden" name="date" value="<%= orderDate%>">
        <input type="hidden" name="total" value="<%= total_Value%>">
        <input type="submit" value="Checkout">
    </form>


</body>
</html>
