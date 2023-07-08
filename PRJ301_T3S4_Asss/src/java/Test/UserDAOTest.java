/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sample.users.UserDAO;
import sample.utils.DBUtils;

/**
 *
 * @author AN515-55
 */
public class UserDAOTest {

    public void testCheckout() throws SQLException, ClassNotFoundException {
        // Create a new UserDAO object
        UserDAO userDAO = new UserDAO();

        // Get the user's ID from the session
        String userId = "1";

        // Update the user's order
        userDAO.updateQuantity("1", 1, userId);

        // Check the quantity of the flower in the user's order
        int quantity = userDAO.getQuantity(userId, "1");

        // Assert that the quantity is 0
        //assertEquals(0, quantity);
    }

    public int getQuantity(String userId, String flowerId) throws SQLException, ClassNotFoundException {

        // Get the connection object
        Connection connection = DBUtils.getConnection();

        // Create the prepared statement
        PreparedStatement statement = connection.prepareStatement(
                "SELECT quantity FROM orders WHERE userId = ? AND flowerId = ?");

        // Set the parameters
        statement.setString(1, userId);
        statement.setString(2, flowerId);

        // Execute the statement
        ResultSet rs = statement.executeQuery();
        int quantity = -1;
        if (rs.next()) {
            quantity = rs.getInt("quantity");
        }

        // Close the connection
        connection.close();
        return quantity;
    }
}
