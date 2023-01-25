/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhtt.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import minhtt.daos.CarDAO;
import minhtt.daos.DiscountCodeDAO;
import minhtt.daos.OrderDAO;
import minhtt.daos.OrderDetailDAO;
import minhtt.dtos.CartDTO;
import minhtt.dtos.UserDTO;

/**
 *
 * @author minhv
 */
public class RentCarController extends HttpServlet {

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            CartDTO cart = (CartDTO) session.getAttribute("CART");
            UserDTO login_user = (UserDTO) session.getAttribute("LOGIN_USER");
            String totalmoney = request.getParameter("totalMoney");
            String discountCode = request.getParameter("txtDiscountCode");

            if (login_user != null && login_user.getRoleID().equals("US")) {
                if (discountCode.isEmpty()) { //usser khong co code giam gia
                    ArrayList<String> carOutOfStock = rentCarNoDiscount(cart, Float.parseFloat(totalmoney), login_user.getEmail());
                    if (carOutOfStock.size() > 0) {
                        request.setAttribute("MESSAGE_RENT_ERROR", "Rent failed, Car(s):" + carOutOfStock + " out of stock, please change amount or time!");
                    } else {
                        request.setAttribute("MESSAGE_RENT", "Rent successful, Totalmoney: " + Float.parseFloat(totalmoney));
                        url = SUCCESS;
                        session.setAttribute("CART", null);
                    }

                } else {//usser co code giam gia
                    DiscountCodeDAO discountDAO = new DiscountCodeDAO();
                    int sale = discountDAO.getSaleOfCode(discountCode);
                    if (sale == 0) {
                        request.setAttribute("MESSAGE_RENT_ERROR", "Discount code is invalid ! ");

                    } else {
                        ArrayList<String> carOutOfStock = rentCarDiscount(cart, Float.parseFloat(totalmoney), discountCode, login_user.getEmail());
                        if (carOutOfStock.size() > 0) {
                            request.setAttribute("MESSAGE_RENT_ERROR", "Rent failed, Car(s):" + carOutOfStock + " out of stock, please change amount or time!");
                        } else {
                            request.setAttribute("MESSAGE_RENT", "Rent successful, Totalmoney: " + (Float.parseFloat(totalmoney) / 100) * (100 - sale) + " (" + sale + "% off) !");
                            url = SUCCESS;
                            session.setAttribute("CART", null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log("Error at RentCarServlet: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    ArrayList<String> rentCarNoDiscount(CartDTO cart, float totalPrice, String email) {
        ArrayList<String> carOutOfStock = new ArrayList<>();
        try {
            CarDAO carDAO = new CarDAO();
            boolean check = true;
            Set<String> key = cart.getCart().keySet();
            for (String id : key) {
                String[] arr=id.split("_");
                int quantity = carDAO.getQuantityOfCar(arr[0]) - carDAO.getNumberCarRented(arr[0], cart.getCart().get(id).getRentalDate(), cart.getCart().get(id).getReturnDate());
                if (cart.getCart().get(id).getQuantity() > quantity) {
                    check = false;
                    carOutOfStock.add(cart.getCart().get(id).getCarName());
                }
            }

            if (check == true) {//them du lieu vao 2 bang tblOrder va tblOrderDetail
                OrderDAO orderDao = new OrderDAO();
                String orderID = "O" + (orderDao.getNoOfOrder() + 1);

                orderDao.createOrder(orderID, email, "NOCODE", totalPrice, cart.getOrderDate());
                OrderDetailDAO orderDetailDao = new OrderDetailDAO();

                for (String id : key) {
                    String[] arr=id.split("_");
                    long getDaysDiff = -1;
                    String[] rental = cart.getCart().get(id).getRentalDate().split("-");
                    String[] returnn = cart.getCart().get(id).getReturnDate().split("-");
                    Date rentalDate = new Date(Integer.parseInt(rental[0]), Integer.parseInt(rental[1]), Integer.parseInt(rental[2]));
                    Date returnDate = new Date(Integer.parseInt(returnn[0]), Integer.parseInt(returnn[1]), Integer.parseInt(returnn[2]));
                    long getDiff = returnDate.getTime() - rentalDate.getTime();
                    getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff) + 1;
                    float price = cart.getCart().get(id).getCarPrice() * getDaysDiff * cart.getCart().get(id).getQuantity();

                    orderDetailDao.createOrderDetail(orderID, arr[0], cart.getCart().get(id).getQuantity(), price, cart.getCart().get(id).getRentalDate(),
                            cart.getCart().get(id).getReturnDate(), Integer.parseInt(Long.toString(getDaysDiff)));
                }
            }
        } catch (Exception e) {
        }
        return carOutOfStock;

    }

    ArrayList<String> rentCarDiscount(CartDTO cart, float totalPrice, String discountCode, String email) {
        ArrayList<String> carOutOfStock = new ArrayList<>();
        try {
            DiscountCodeDAO discountDAO = new DiscountCodeDAO();
            int sale = discountDAO.getSaleOfCode(discountCode);
            CarDAO carDAO = new CarDAO();
            boolean check = true;
            Set<String> key = cart.getCart().keySet();
            for (String id : key) {
                String[] arr=id.split("_");
                int quantity = carDAO.getQuantityOfCar(arr[0]) - carDAO.getNumberCarRented(arr[0], cart.getCart().get(id).getRentalDate(), cart.getCart().get(id).getReturnDate());
                if (cart.getCart().get(id).getQuantity() > quantity) {
                    check = false;
                    carOutOfStock.add(cart.getCart().get(id).getCarName());
                }
            }

            if (check == true) {//them du lieu vao 2 bang tblOrder va tblOrderDetail
                OrderDAO orderDao = new OrderDAO();
                String orderID = "O" + (orderDao.getNoOfOrder() + 1);
                totalPrice = totalPrice - ((totalPrice * sale) / 100);

                orderDao.createOrder(orderID, email, discountCode, totalPrice, cart.getOrderDate());
                discountDAO.updateStatusCode(discountCode);
                OrderDetailDAO orderDetailDao = new OrderDetailDAO();

                for (String id : key) {
                    String[] arr=id.split("_");
                    long getDaysDiff = -1;
                    String[] rental = cart.getCart().get(id).getRentalDate().split("-");
                    String[] returnn = cart.getCart().get(id).getReturnDate().split("-");
                    Date rentalDate = new Date(Integer.parseInt(rental[0]), Integer.parseInt(rental[1]), Integer.parseInt(rental[2]));
                    Date returnDate = new Date(Integer.parseInt(returnn[0]), Integer.parseInt(returnn[1]), Integer.parseInt(returnn[2]));
                    long getDiff = returnDate.getTime() - rentalDate.getTime();
                    getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff) + 1;
                    float price = (cart.getCart().get(id).getCarPrice() * getDaysDiff * cart.getCart().get(id).getQuantity()) / 100 * (100 - sale);

                    orderDetailDao.createOrderDetail(orderID, arr[0], cart.getCart().get(id).getQuantity(), price, cart.getCart().get(id).getRentalDate(),
                            cart.getCart().get(id).getReturnDate(), Integer.parseInt(Long.toString(getDaysDiff)));
                }
            }
        } catch (Exception e) {
        }
        return carOutOfStock;

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
