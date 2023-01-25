/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import minhtt.daos.CarDAO;
import minhtt.daos.OrderDetailDAO;
import minhtt.dtos.CarDTO;

/**
 *
 * @author minhv
 */
public class SearchController extends HttpServlet {

    private final static String SUCCESS = "rentPage.jsp";
    private final static String ERROR = "rentPage.jsp";

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
            String carName = request.getParameter("txtCarName");
            String categoryID = request.getParameter("cmbCategory");
            String rentalDate = request.getParameter("txtRentalDate");
            String returnDate = request.getParameter("txtReturnDate");
            String amount = request.getParameter("txtAmount");
            String currentPage = request.getParameter("txtCurrentPage");
            long getDaysDiff = -1;
            if (currentPage == null) {
                currentPage = "1";
            }
            if (carName.isEmpty()) {
                carName = "";
            }
            if (amount.isEmpty()) {
                amount = "1";
            }
            CarDAO carDao = new CarDAO();
            OrderDetailDAO orderDetailDAO=new OrderDetailDAO();
            if (!rentalDate.isEmpty() && !returnDate.isEmpty()) {
                String[] rental = rentalDate.split("-");
                String[] returnn = returnDate.split("-");
                Date rentalDate1 = new Date(Integer.parseInt(rental[0]), Integer.parseInt(rental[1]), Integer.parseInt(rental[2]));
                Date returnDate1 = new Date(Integer.parseInt(returnn[0]), Integer.parseInt(returnn[1]), Integer.parseInt(returnn[2]));
                long getDiff = returnDate1.getTime() - rentalDate1.getTime();
                getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
            }

            if (rentalDate.isEmpty() || returnDate.isEmpty()) {
                request.setAttribute("ERROR_MESSAGE", "Please input Rental date and Return date");
            } else if (getDaysDiff + 1 < 1) {
                request.setAttribute("ERROR_MESSAGE", "Return date must be greater than or equal to Rental date");
            } else {
                List<CarDTO> listCarFree =carDao.getListCarFree(carName, categoryID, rentalDate, returnDate, Integer.parseInt(amount));
                List<CarDTO> listCarRented =carDao.getListCarRentedInPeriod(carName, categoryID, rentalDate, returnDate, Integer.parseInt(amount));
                               
                if(listCarFree==null){
                    listCarFree=new ArrayList<>();
                    if (listCarRented != null) {
                        for (CarDTO carDTO : listCarRented) {
                            int numberRented = carDao.getNumberCarRented(carDTO.getCarID(), rentalDate, returnDate);
                            if ((carDTO.getQuantity() - numberRented) >= Integer.parseInt(amount)) {
                                carDTO.setQuantity(carDTO.getQuantity() - numberRented);
                                listCarFree.add(carDTO);
                            }
                        }
                    }
                }else{
                    if (listCarRented != null) {
                        for (CarDTO carDTO : listCarRented) {
                            int numberRented = carDao.getNumberCarRented(carDTO.getCarID(), rentalDate, returnDate);
                            if ((carDTO.getQuantity() - numberRented) >= Integer.parseInt(amount)) {
                                carDTO.setQuantity(carDTO.getQuantity() - numberRented);
                                listCarFree.add(carDTO);
                            }
                        }
                    }
                }
                                                                                              
                int count = listCarFree.size();
                int totalPage = 0;
                if (count % 20 != 0) {
                    totalPage = count / 20 + 1;
                } else {
                    totalPage = count / 20;
                }                
                request.setAttribute("TOTAL_PAGE", totalPage);
                                
                
                List<CarDTO> listTemp = new ArrayList<>();
                for (int i = (Integer.parseInt(currentPage) - 1) * 20; i < (Integer.parseInt(currentPage) - 1) * 20 + 20; i++) {
                    if(i<listCarFree.size()){
                        if (orderDetailDAO.getAverageRating(listCarFree.get(i).getCarID()).equals("There are no rating yet for this car")) {
                            listCarFree.get(i).setRating(orderDetailDAO.getAverageRating(listCarFree.get(i).getCarID()));
                            listTemp.add(listCarFree.get(i));
                        }else{
                            listCarFree.get(i).setRating(orderDetailDAO.getAverageRating(listCarFree.get(i).getCarID())+" score(s) ("+orderDetailDAO.getNumberOfRating(listCarFree.get(i).getCarID())+" rating)");
                            listTemp.add(listCarFree.get(i));
                        }    
                        
                    }                   
                }
                
                Collections.sort(listTemp);
                            
                if (listCarFree != null && listCarFree.size()>0 && listTemp != null && listTemp.size()>0) {
                    url = SUCCESS;
                    request.setAttribute("LIST_CAR", listTemp);
                } else {
                    request.setAttribute("MESSAGE", "No result for search");
                }
            }

        } catch (Exception e) {
            log("Error at SearchServlet " + e.toString());
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
