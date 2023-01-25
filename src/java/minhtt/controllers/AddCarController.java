/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtt.daos.CarDAO;
import minhtt.dtos.CarDTO;
import minhtt.dtos.CartDTO;
import minhtt.dtos.UserDTO;

/**
 *
 * @author minhv
 */
public class AddCarController extends HttpServlet {

    private final static String ERROR = "login.jsp";
    private final static String SUCCESS = "SearchController";

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
            UserDTO login_user = (UserDTO) session.getAttribute("LOGIN_USER");
            if (login_user != null && login_user.getRoleID().equals("US")) {
                String carID = request.getParameter("txtCarID");
                String categoryID = request.getParameter("txtCategoryID");
                String carName = request.getParameter("txtCarName1");
                String color = request.getParameter("txtColor");
                String year = request.getParameter("txtYear");
                int quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                float carPrice = Float.parseFloat(request.getParameter("txtPrice"));
                String linkImg = request.getParameter("txtImg");

                String rentalDate = request.getParameter("txtRentalDate");
                String returnDate = request.getParameter("txtReturnDate");

                long getDaysDiff = -1;
                String[] rental = rentalDate.split("-");
                String[] returnn = returnDate.split("-");
                Date rentalDate1 = new Date(Integer.parseInt(rental[0]), Integer.parseInt(rental[1]), Integer.parseInt(rental[2]));
                Date returnDate1 = new Date(Integer.parseInt(returnn[0]), Integer.parseInt(returnn[1]), Integer.parseInt(returnn[2]));
                long getDiff = returnDate1.getTime() - rentalDate1.getTime();
                getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);

                CartDTO cart = (CartDTO) session.getAttribute("CART");
                CarDAO carDAO = new CarDAO();

                if (cart == null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    Date date = new Date();
                    String strCurrentDate = formatter.format(date);
                    cart = new CartDTO(login_user.getFullName(), 0, strCurrentDate, null, null);
                    CarDTO carDTO = new CarDTO(carID, categoryID, carName, color, year, carPrice, 1, rentalDate, returnDate, getDaysDiff + 1, linkImg, null, true);
                    cart.add(carDTO);
                    session.setAttribute("CART", cart);
                    request.setAttribute("MESSAGE", "You have added car successful , amount of " + cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getCarName() + " from " + rentalDate + " to " + returnDate + " in cart :" + cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getQuantity());
                    url = SUCCESS;
                } else {
                    if (cart.getCart().containsKey(carID + "_" + rentalDate + "_" + returnDate)) {
                        if (cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getQuantity() >= carDAO.getQuantityOfCar(carID) - carDAO.getNumberCarRented(carID, rentalDate, returnDate)) {
                            request.setAttribute("MESSAGE", "You can not add more this car !");
                            url = SUCCESS;
                        } else {
                            if (checkAdd(carID, rentalDate, returnDate, cart) == true) {
                                if (carDAO.getQuantityOfCar(carID) - carDAO.getNumberCarRented(carID, rentalDate, returnDate) <= 0) {
                                    request.setAttribute("MESSAGE", "You can not add more this car !");
                                    url = SUCCESS;
                                } else {
                                    CarDTO carDTO = new CarDTO(carID, categoryID, carName, color, year, carPrice, 1, rentalDate, returnDate, getDaysDiff + 1, linkImg, null, true);
                                    cart.add(carDTO);
                                    session.setAttribute("CART", cart);
                                    request.setAttribute("MESSAGE", "You have added car successful , amount of " + cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getCarName() + " from " + rentalDate + " to " + returnDate + " in cart :" + cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getQuantity());
                                    url = SUCCESS;
                                }
                            } else {
                                request.setAttribute("MESSAGE", "You can not add more this car !");
                                url = SUCCESS;
                            }
                        }
                    } else {
                        if (checkAdd(carID, rentalDate, returnDate, cart) == true) {
                            if (carDAO.getQuantityOfCar(carID) - carDAO.getNumberCarRented(carID, rentalDate, returnDate) <= 0) {
                                request.setAttribute("MESSAGE", "You can not add more this car !");
                                url = SUCCESS;
                            } else {
                                CarDTO carDTO = new CarDTO(carID, categoryID, carName, color, year, carPrice, 1, rentalDate, returnDate, getDaysDiff + 1, linkImg, null, true);
                                cart.add(carDTO);
                                session.setAttribute("CART", cart);
                                request.setAttribute("MESSAGE", "You have added car successful , amount of " + cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getCarName() + " from " + rentalDate + " to " + returnDate + " in cart :" + cart.getCart().get(carID + "_" + rentalDate + "_" + returnDate).getQuantity());
                                url = SUCCESS;
                            }
                        }
                        else {
                            request.setAttribute("MESSAGE", "You can not add more this car !");
                            url = SUCCESS;
                        }

                    }
                }

            } else if (login_user == null) {
                session.setAttribute("LOGIN_INVALID", "You must login to add car !");
            }
        } catch (Exception e) {
            log("Error at AddCarServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    boolean checkAdd(String carID, String rentalDate, String returnDate, CartDTO cart) {
        boolean result = true;
        try {
            CarDAO carDAO = new CarDAO();
            Set<String> key = cart.getCart().keySet();
            int quantityCart=0;
            for (String string : key) {
                String[] arr = string.split("_");

                if (arr[0].equals(carID)) {
                    
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
            if (carDAO.getQuantityOfCar(carID) - carDAO.getNumberCarRented(carID, rentalDate, returnDate) - quantityCart <= 0) {
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
