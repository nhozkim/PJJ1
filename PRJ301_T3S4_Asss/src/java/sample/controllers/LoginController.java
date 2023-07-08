/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.users.Cart;

import sample.users.UserDAO;
import sample.users.UserDTO;

@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String US = "US";
    private static final String USER_PAGE = "user.jsp";
    private static final String AD = "AD";
    private static final String ADMIN_PAGE = "admin.jsp";
    private static final String CHECKOUT_PAGE = "checkout.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        String action = request.getParameter("action");
        if (action == null) {
            action = "Login";
        }
        if (action.equals("Logout")) {
            action = "Logout";

            // Invalidate the user's session
            HttpSession session = request.getSession();
            session.invalidate();

            // Redirect the request to the login page
            response.sendRedirect("login.jsp");
        } else {
            // The user is not trying to logout, so continue with the normal processing of the request
            try {
                String userID = request.getParameter("userID");
                String password = request.getParameter("password");
                System.out.println("userID: " + userID);
                System.out.println("password: " + password);
                UserDAO dao = new UserDAO();
                UserDTO loginUser = dao.checkLogin(userID, password);

                if (loginUser != null) {
                    // The user is logged in, continue with the checkout process
                    String orderID = request.getParameter("orderID");
                    String checkoutUserID = request.getParameter("userID");
                    String date = request.getParameter("date");
                    String total = request.getParameter("total");
                    System.out.println("orderID: " + orderID);
                    System.out.println("date: " + date);
                    System.out.println("total: " + total);
                    request.setAttribute("orderID", userID);
                    request.setAttribute("checkoutUserID", checkoutUserID);
                    request.setAttribute("date", date);
                    request.setAttribute("total", total);
                    // Process the order
//                    // Send the user a confirmation email
//                    url = "/checkout.jsp";
//                    request.getRequestDispatcher(url).forward(request, response);
                    //phan quyen
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", loginUser);

                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart != null && cart.getItemCount() > 0) {
                        // There is a pending order, redirect the user to the checkout page
                        url = CHECKOUT_PAGE;
                    } else {
                        String roleID = loginUser.getRoleID();
                        if (AD.equals(roleID)) {
                            url = ADMIN_PAGE;
                            request.getRequestDispatcher(ADMIN_PAGE).forward(request, response);
                        } else if (US.equals(roleID)) {
                            url = USER_PAGE;
                            request.getRequestDispatcher(USER_PAGE).forward(request, response);
                        } else {
                            request.setAttribute("ERROR", "Your role is not supported!");
                            url = LOGIN_PAGE;
                        }
                    }
                } else {
                    request.setAttribute("ERROR", loginUser);
                    url = LOGIN_PAGE;
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } catch (Exception e) {
                log("Error at LoginController: " + e.toString());
            } finally {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
