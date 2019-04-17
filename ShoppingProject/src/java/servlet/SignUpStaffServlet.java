/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import errors.RegistrationErrors;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import users.dao.UsersDAO;
import utils.CheckMD5;

/**
 *
 * @author tabal
 */
public class SignUpStaffServlet extends HttpServlet {
    
    private final String signUpError = "create-new-staff.jsp";
    private final String loadStaffServlet = "LoadStaffServlet";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        RegistrationErrors errors = new RegistrationErrors();
        String name = request.getParameter("txtUserName");
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String retypePassword = request.getParameter("txtRetypePassword");

        String url = signUpError;
        try {
            boolean error = false;

            if (name.trim().length() < 6 || name.trim().length() > 30) {
                error = true;
                errors.setNameLengthErr("Name required 6 - 30 characters.");
            }
            if (!email.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                error = true;
                errors.setEmailFormatErr("Email incorrect format (example@example.com).");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                error = true;
                errors.setPasswordLengthErr("Password required 6 - 30 characters.");
            } else if (!retypePassword.trim().equals(password.trim())) {
                error = true;
                errors.setConfirmNotMatch("Password and Retype Password must be matched.");
            }

            if (error) {
                request.setAttribute("SIGNUPERROR", errors);
            } else {
                UsersDAO dao = new UsersDAO();
                String md5Password = CheckMD5.getMD5(password);
                boolean result = dao.signUp(name, email, md5Password, 2);

                if (result) {
                    url = loadStaffServlet;
                }
            }

            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (SQLException e) {
            log("SignUpServlet_SQL " + e.getMessage());
            errors.setEmailIsExist("Email is exist.");

            request.setAttribute("SIGNUPERROR", errors);

            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        } catch (ClassNotFoundException e) {
            log("SignUpServlet_CNF " + e.getMessage());
        } finally {
            out.close();
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
