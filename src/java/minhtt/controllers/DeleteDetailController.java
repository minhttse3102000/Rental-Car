/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtt.daos.OrderDAO;
import minhtt.daos.OrderDetailDAO;
import minhtt.dtos.CarDTO;

/**
 *
 * @author minhv
 */
public class DeleteDetailController extends HttpServlet {

    private final static String SUCCESS = "detail.jsp";
    private final static String ERROR = "detail.jsp";
    private final static String RATING = "SendRatingController";

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            List<CarDTO> listDetail = (List<CarDTO>) session.getAttribute("LIST_DETAIL");
            String orderDetailID = request.getParameter("txtCarID");
            String action = request.getParameter("btnAction");

            if (action.equals("Delete")) {
                OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
                OrderDAO orderDAO = new OrderDAO();
                if ((orderDetailDAO.getStatus(orderDetailID))==true) {
                    orderDetailDAO.delete(orderDetailID);
                    float price = orderDetailDAO.getPrice(orderDetailID);
                    orderDAO.updateTotalMoney(orderDetailID, price);
                    for (CarDTO carDTO : listDetail) {
                        if (carDTO.getCarID().equals(orderDetailID)) {
                            carDTO.setStatus(false);
                        }
                    }

                    url = SUCCESS;
                    request.setAttribute("MESSAGE", "Delete detail successful, We will pay you back");
                    session.setAttribute("LIST_DETAIL", listDetail);
                }
            } else if (action.equals("Send Rating")) {
                url = RATING;
            }

        } catch (Exception e) {
            log("Error at DeleteDetailServlet " + e.toString());
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
