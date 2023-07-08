package sample.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import sample.utils.DBUtils;
import static sample.utils.DBUtils.getConnection;

/**
 *
 * @author AN515-55
 */
public class Cart {

    private Map<String, Flower> cart;

    public Cart() {
    }

    public Cart(Map<String, Flower> cart) {
        this.cart = cart;
    }

    public Map<String, Flower> getCart() {
        return cart;
    }

    public void setCart(Map<String, Flower> cart) {
        this.cart = cart;
    }

    public boolean add(String id, Flower flower) {
        boolean check = false;
        try {
            System.out.println("Adding item to cart: id = " + id + ", flower = " + flower);

            if (this.cart == null) {
                this.cart = new HashMap<>();
            }
            if (this.cart.containsKey(id)) {
                int currentQuantity = this.cart.get(id).getQuantity();
                flower.setQuantity(currentQuantity + flower.getQuantity());
            }
            this.cart.put(id, flower);
            check = true;

            System.out.println("Cart after adding item: " + this.cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public boolean add1(String id, Flower flower) {
        boolean check = false;
        try {
            // neu gio hang chua co thi tao moi gio hang

            if (this.cart == null) {
                this.cart = new HashMap<>();
            }
            // neu san pham da co trong gio hang thji cap nhat lai quantity
            if (this.cart.containsKey(id)) {
                int currentQuantity = this.cart.get(id).getQuantity();
                flower.setQuantity(currentQuantity + flower.getQuantity());
            }
            this.cart.put(id, flower);
            check = true;

        } catch (Exception e) {

        }
        return check;
    }

    public boolean remove(String id) {
        boolean check = false;
        try {
            if (this.cart != null) {
                if (this.cart.containsKey(id)) {
                    this.cart.remove(id);
                    check = true;
                }
            }

        } catch (Exception e) {

        }
        return check;
    }

    public boolean edit(String id, Flower fl) {
        boolean check = false;
        try {
            if (this.cart != null) {
                if (this.cart.containsKey(id)) {
                    this.cart.replace(id, fl);

                    check = true;
                }
            }

        } catch (Exception e) {

        }
        return check;
    }

    public void checkout() throws SQLException, ClassNotFoundException {
        // Get the order ID
        Connection connection = DBUtils.getConnection();
        String orderId = getNextOrderId(connection);

        // Iterate through the cart and insert data into the tblOrderDetail table
        for (Map.Entry<String, Flower> entry : cart.entrySet()) {
            String productId = entry.getKey();
            Flower flower = entry.getValue();

            // Insert data into the tblOrderDetail table
            String sql = "INSERT INTO tblOrderDetail (orderId, productId, quantity) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, orderId);
            statement.setString(2, productId);
            statement.setInt(3, flower.getQuantity());
            statement.executeUpdate();
        }

        // Update the quantity of the products in the tblProducts table
        for (Map.Entry<String, Flower> entry : cart.entrySet()) {
            String productId = entry.getKey();
            Flower flower = entry.getValue();

            // Update the quantity of the product in the tblProducts table
            String sql = "UPDATE tblProducts SET quantity = quantity - ? WHERE productId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, flower.getQuantity());
            statement.setString(2, productId);
            statement.executeUpdate();
        }

        // Clear the cart
        cart = null;
    }

    private String getNextOrderId(Connection connection) throws SQLException {
        String sql = "SELECT MAX(orderId) + 1 AS nextOrderId FROM tblOrder";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("nextOrderId");
        } else {
            return "1";
        }
    }

    public int getItemCount() {

        if (cart == null) {
            return 0;
        } else {
            return cart.size();
        }
    }

}
