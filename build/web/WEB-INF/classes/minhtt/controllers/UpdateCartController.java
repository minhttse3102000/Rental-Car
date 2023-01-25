/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtt.daos.CarDAO;
import minhtt.dtos.CartDTO;

/**
 *
 * @author minhv
 */
public class UpdateCartController extends HttpServlet {
    private static final String SUCCESS = "billPage.jsp";
    private static final String ERROR = "billPage.jsp";

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
        String url=ERROR;
        try {
            String rentalDateCart=request.getParameter("txtRentalDateCart");
            String returnDateCart=request.getParameter("txtReturnDateCart");
            String carID=request.getParameter("txtCarID");
            String amount=request.getParameter("txtAmount");

            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
                                                
            if(rentalDateCart.isEmpty() || returnDateCart.isEmpty()){
                request.setAttribute("ERROR_UPDATE_MESSAGE", "Rental date and Return date must not be empty ! ");
            }else if (!amount.matches("[0-9]*") || amount.matches("[0]*")) {
                request.setAttribute("ERROR_UPDATE_MESSAGE", "Amount is number more than 0 ! ");
            }else{     
                CarDAO carDAO =new CarDAO();
                int quantity= carDAO.getQuantityOfCar(carID)-carDAO.getNumberCarRented(carID, rentalDateCart, returnDateCart);
               
                long getDaysDiff = -1;
                String[] rental = rentalDateCart.split("-");
                String[] returnn = returnDateCart.split("-");
                Date rentalDate = new Date(Integer.parseInt(rental[0]), Integer.parseInt(rental[1]), Integer.parseInt(rental[2]));
                Date returnDate = new Date(Integer.parseInt(returnn[0]), Integer.parseInt(returnn[1]), Integer.parseInt(returnn[2]));
                long getDiff = returnDate.getTime() - rentalDate.getTime();
                getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
                
                if(getDaysDiff+1 < 1){
                    request.setAttribute("ERROR_UPDATE_MESSAGE", "Return date must be greater than or equal to Rental date ! ");                                     
                }else if (Integer.parseInt(amount)>quantity){
                    request.setAttribute("ERROR_UPDATE_MESSAGE", "Can not update because not enough quantity at time !");
                }else{                
                    if (checkUpdate(carID, rentalDateCart, returnDateCart, cart,Integer.parseInt(amount)) == true) {                        
                        if (carDAO.getQuantityOfCar(carID) - carDAO.getNumberCarRented(carID, rentalDateCart, returnDateCart) <= 0) {
                            request.setAttribute("ERROR_UPDATE_MESSAGE", "Can not update because not enough quantity at time !");
                        } else {
                            cart.update(carID, Integer.parseInt(amount), rentalDateCart, returnDateCart, getDaysDiff + 1);
                            request.setAttribute("ERROR_UPDATE_MESSAGE", "Update cart successful !");
                            session.setAttribute("CART", cart);
                            url = SUCCESS;
                        }
                                             
                    } else {
                        request.setAttribute("ERROR_UPDATE_MESSAGE", "Can not update because not enough quantity at time !");
                    }                  
                }                             
            }
        } catch (Exception e) {
            log("Error at UpdateCartSerlvet: " + e.toString());
        }finally{
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    boolean checkUpdate(String carID, String rentalDate, String returnDate, CartDTO cart,int amount) {
        boolean result = true;
        try {
            CarDAO carDAO = new CarDAO();
            Set<String> key = cart.getCart().keySet();
            int quantityCart=0;
            for (String string : key) {
                String[] arr = string.split("_");

                if (arr[0].equals(carID) && !string.equals(carID+"_"+rentalDate+"_"+returnDate)) {
                    
                    String[] rentalAdd = rentalDate.split("-");
                    String[] returnnAdd = returnDate.split("-");
                    String[] rentalCart = arr[1].split("-");
                    String[] returnnCart = arr[2].split("-");
                    
                    Date rentalAddDate = new Date(Integer.parseInt(rentalAdd[0]), Integer.parseInt(rentalAdd[1]), Integer.parseInt(rentalAdd[2]));
                    Date returnAddDate = new Date(Integer.parseInt(returnnAdd[0]), Integer.parseInt(returnnAdd[1]), Integer.parseInt(returnnAdd[2]));

                    Date rentalCartDate = new Date(Integer.parseInt(rentalCart[0]), Integer.parseInt(rentalCart[1]), Integer.parseInt(rentalCart[2]));
                    Date returnCartDate = new Date(Integer.parseInt(returnnCart[0]), Integer.parseInt(returnnCart[1]), Integer.parseInt(returnnCart[2]));

                    if ((TimeUnit.MILLISECONDS.toDays(returnCartDate.getTime() - rentalAddDate.getTime()) >= 0 && TimeUnit.MILLISECONDS.toDays(rentalCartDate.getTime() - returnAddDate.getTime()) <= 0)
                            || (TimeUnit.MILLISECONDS.toDays(returnAddDate.getTime() - rentalCartDate.getTime()) >= 0 && TimeUnit.MILLISECONDS.toDays(rentalAddDate.getTime() - returnCartDate.getTime()) <= 0)
                            || (TimeUnit.MILLISECONDS.toDays(rentalCartDate.getTime() - rentalAddDate.getTime()) <= 0 && TimeUnit.MILLISECONDS.toDays(returnCartDate.getTime() - returnAddDate.getTime()) >= 0)) {

                        quantityCart = quantityCart + cart.getCart().get(string).getQuantity();
                    }

                }

            }
            if ((carDAO.getQuantityOfCar(carID)- carDAO.getNumberCarRented(carID, rentalDate, returnDate)) < (amount+ quantityCart) ) {
                result = false;
            }
        } catch (Exception e) {
        }

        return result;

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
