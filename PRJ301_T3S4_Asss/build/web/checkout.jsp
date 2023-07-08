<%-- 
    Document   : checkout
    Created on : Jul 8, 2023, 5:37:57 AM
    Author     : AN515-55
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Checkout</title>
    </head>
    <body>

        <h1>Checkout</h1>

        <p>Thank you for your order!</p>

        <p>Your order ID is <b>${orderID}</b>.</p>

        <p>Your order was placed by <b>${checkoutUserID}</b>.</p>

        <p>Your order was placed on <b>${date}</b>.</p>

        <p>Your order total is <b>${total}</b>.</p>

    </body>
</html>