/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import sample.utils.DBUtils;

public class UserDAO {

    private static final String LOGIN = "SELECT fullName,roleID FROM tblUsers  WHERE userID = ? AND password = ?";
    private static final String SEARCH = "SELECT userID, fullName,roleID FROM tblUsers  WHERE fullName LIKE ?";
    private static final String DELETE = "DELETE FROM tblUsers  WHERE userID = ?";
    private static final String UPDATE = "UPDATE  tblUsers SET fullName = ?, roleID = ?  WHERE userID = ?";
    private static final String CHECK_DUPLICATE = "SELECT roleID FROM tblUsers  WHERE userID = ?";
    private static final String INSERT = "INSERT INTO tblUsers (userID, fullName,roleID,password) VALUES (?,?,?,?)";

    public UserDTO checkLogin(String userID, String password) throws SQLException {
         System.out.println("Checking login for userID: " + userID + ", password: " + password);
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
//            Xu ly o day
            conn = DBUtils.getConnection();
            ptm = conn.prepareStatement(LOGIN);
            System.out.println("SQL query: " + LOGIN);
            ptm.setString(1, userID);
            ptm.setString(2, password);
            rs = ptm.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("fullName");
                String roleID = rs.getString("roleID");
                user = new UserDTO(userID, fullName, roleID, password);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        System.out.println("Returning loginUser: " + user);
        return user;
    }

    public List<UserDTO> getListUser(String search) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        List<UserDTO> listUser = new ArrayList<>();
        try {
//            Xu ly o day
            conn = DBUtils.getConnection();
            if (conn != null) {

                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");

                rs = ptm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String password = "***";
                    listUser.add(new UserDTO(userID, fullName, roleID, password));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listUser;
    }

    public boolean update(UserDTO user) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, user.getFullName());
                ptm.setString(2, user.getRoleID());
                ptm.setString(3, user.getPassword());
                ptm.setString(4, user.getUserID());
                check = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean deleteUser(String userID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
//            Xu ly o day
            conn = DBUtils.getConnection();

            if (conn != null) {

                ptm = conn.prepareStatement(DELETE);
                ptm.setString(1, userID);

                check = ptm.executeUpdate() > 0 ? true : false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return check;
    }

    public boolean checkDuplicate(String userID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {

                ptm = conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setString(1, userID);

                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;

    }

    public boolean insert(UserDTO user) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getFullName());
                ptm.setString(3, user.getRoleID());
                ptm.setString(4, user.getPassword());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean insertV2(UserDTO user) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getFullName());
                ptm.setString(3, user.getRoleID());
                ptm.setString(4, user.getPassword());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public List<Flower> getListProduct(String search) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        List<Flower> listFlower = new ArrayList<>();
        try {
//            Xu ly o day
            conn = DBUtils.getConnection();
            if (conn != null) {

                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");

                rs = ptm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("productID");
                    String name = rs.getString("productName");
                    double price = rs.getDouble("productPrice");
                    int quanity = rs.getInt("quantity");
                    listFlower.add(new Flower(id, name, price, quanity));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listFlower;
    }

    public void checkout(String flowerId, int quantity, HttpServletRequest request) throws SQLException, ClassNotFoundException {
        Connection connection = DBUtils.getConnection();
        // Get the user's ID from the session
        String userId = (String) request.getSession().getAttribute("userId");

        // Update the user's order
        UserDAO userDAO = new UserDAO();
        userDAO.updateQuantity(flowerId, quantity, userId);

        // Close the connection
        connection.close();
    }

    public void updateQuantity(String flowerId, int quantity, String userId) throws SQLException, ClassNotFoundException {
        // Get the connection object
        Connection connection = DBUtils.getConnection();

        // Create the prepared statement
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE orders SET quantity = quantity - ? WHERE flowerId = ? AND userId = ?");

        // Set the parameters
        statement.setInt(1, quantity);
        statement.setString(2, flowerId);
        statement.setString(3, userId);

        // Execute the statement
        statement.executeUpdate();

        // Close the connection
        connection.close();
    }

    public int updateQuantity1(String flowerId, int quantity, String userId) throws SQLException, ClassNotFoundException {

        // Check if the user owns the flower
        if (!checkDuplicate(userId, flowerId)) {
            return -1;
        }

        // Update the quantity of the flower in the user's order
        Connection connection = DBUtils.getConnection();

        // Create the prepared statement
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE orders SET quantity = ? WHERE userId = ? AND flowerId = ?");

        // Set the parameters
        statement.setInt(1, quantity);
        statement.setString(2, userId);
        statement.setString(3, flowerId);

        // Execute the statement
        statement.executeUpdate();

        // Close the connection
        connection.close();

        return 1;
    }

    public int getQuantity(String userId, String flowerId) throws SQLException, ClassNotFoundException {
        if (!checkDuplicate(userId)) {
            return -1;
        }
        if (flowerId == null) {
            return -1;
        }

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

    public boolean checkDuplicate(String userId, String flowerId) throws SQLException, ClassNotFoundException {
        // Get the connection object
        Connection connection = DBUtils.getConnection();

        // Create the prepared statement
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM orders WHERE userId = ? AND flowerId = ?");

        // Set the parameters
        statement.setString(1, userId);
        statement.setString(2, flowerId);

        // Execute the statement
        ResultSet rs = statement.executeQuery();

        // Check if the result set has any rows
        boolean hasRow = rs.next();

        // Close the connection
        connection.close();

        // If the user does not own the flower, return false
        if (!hasRow) {
            return false;
        }

        return true;
    }
}
