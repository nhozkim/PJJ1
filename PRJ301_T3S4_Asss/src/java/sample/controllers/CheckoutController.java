/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.users.Cart;
import sample.users.UserDAO;
import sample.users.UserDTO;

/**
 *
 * @author AN515-55
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;

        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(userID, password);

            if (loginUser == null) {
                // The user is not logged in, forward to the login page
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                // The user is logged in, continue with the checkout process
                String orderID = request.getParameter("orderID");
                String checkoutUserID = loginUser.getUserID();
                String date = request.getParameter("date");
                String total = request.getParameter("total");
                System.out.println("orderID: " + orderID);
                System.out.println("checkoutUserID: " + checkoutUserID);
                System.out.println("date: " + date);
                System.out.println("total: " + total);
                System.out.println("Forwarding to: " + url);
                request.getRequestDispatcher(url).forward(request, response);
                // Process the order
                request.setAttribute("orderID", orderID);
                request.setAttribute("checkoutUserID", checkoutUserID);
                request.setAttribute("date", date);
                request.setAttribute("total", total);

                // Send the user a confirmation email
                url = "/checkout.jsp";
                request.getRequestDispatcher(url).forward(request, response);
            }

        } catch (Exception e) {
            log("Error at LoginController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
