/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import users.dao.UsersDAO;
import users.dto.UsersDTO;

/**
 *
 * @author tabal
 */
public class LoadStaffServlet extends HttpServlet {
    private final String staffPage = "staff.jsp";

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
        HttpSession session = request.getSession();
        try {
            UsersDAO dao = new UsersDAO();
            int first = 0;
            int last = 5;
            int pages = 1;
            // Get number of staff int DB.
            int totalStaff = dao.getCountListStaff();
            if (totalStaff <= 5) {
                last = totalStaff;
            } else {
                pages = totalStaff / 5;
                if (totalStaff % 5 > 0) {
                    pages++;
                }
            }
            // Get current button.
            List<Integer> currentButton = (List<Integer>) session.getAttribute("CURRENTSTAFFBUTTON");
            // Get current page number.
            int currButon = 1;
            if (currentButton != null) {
                currButon = currentButton.get(0);
                if (currButon == 1) {
                    first = 0;
                } else {
                    last = (currButon - 1) * 5;
                    first = last;
                }
                last = 5;
            }
            dao.getListStaffFromTo(first, last);
            List<UsersDTO> listStaff = dao.getListStaff();
            session.setAttribute("LISTSTAFF", listStaff);
            
            // Add number of button page at listPageTemp.
            List<Integer> listPageTemp = new ArrayList<>();
            for (int i = 0; i < pages; i++) {
                listPageTemp.add(i + 1);
            }
            // Check if number of pages greater 5
            // jst show 5 buttons.
            List<Integer> listPage = new ArrayList<>();
            if (listPageTemp.size() > 5) {
                int currPos = 0;
                int start = 0;
                // Middle button.
                final int middle = 5 / 2;
                int end = 4;
                if (currButon != 0) {
                    // Get position current button by value subtraction 1.
                    currPos = currButon - 1;
                }
                if (currPos > middle) {
                    if (currPos == listPageTemp.size() - 2) {
                        start = currPos - 3;
                        end = listPageTemp.size() - 1;
                    } else if (currPos == listPageTemp.size() - 1) {
                        start = currPos - 4;
                        end = listPageTemp.size() - 1;
                    } else {
                        start = currPos - middle;
                        end = currButon + 2;
                        if (end >= listPageTemp.size()) {
                            end = listPageTemp.size() - 1;
                        }
                    }
                }
                for (int i = start; i <= end; i++) {
                    listPage.add(listPageTemp.get(i));
                }
                session.setAttribute("PAGESSTAFF", listPage);
            } else {
                session.setAttribute("PAGESSTAFF", listPageTemp);
            }
            RequestDispatcher rd = request.getRequestDispatcher(staffPage);
            rd.forward(request, response);
        } catch (SQLException e) {
            log("LoadStaffServlet_SQL " + e.getMessage());
        } catch (ClassNotFoundException e) {
            log("LoadStaffServlet_CNF " + e.getMessage());
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
