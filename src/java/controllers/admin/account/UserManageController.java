/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin.account;

import dao.account.AddressDAO;
import dao.account.UserDAO;
import dao.product.MealDAO;
import dto.account.User;
import dto.product.Meal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@WebServlet(urlPatterns = {"/admin/account/UserManageController"})
public class UserManageController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String URL_PRODUCT_MANAGE = "/AMainController?action=userManagePage";
    private final String DELETE_USER_URL = "/AMainController?action=deleteUser";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        UserDAO dao = new UserDAO();
        HttpSession session = request.getSession();

        // Get the numPage parameter from the request
        String numPageStr = request.getParameter("numPage");

        int numPage = numPageStr != null ? Integer.parseInt(numPageStr)
                : session.getAttribute("numPage") != null
                ? (int) session.getAttribute("numPage") : 1;

        //create table if null
        if (session.getAttribute("userList") == null) {
            List<User> list = dao.getAllUser();
            session.setAttribute("userList", list);
        }

        List<User> list = (List<User>) session.getAttribute("userList");

        //search group of user
        List<User> copyList = findUserGroup(request, dao);
        if (copyList != null) {
            session.setAttribute("userList", copyList);
        }

        //search name
        copyList = searchingUsers(request, dao);
        if (copyList != null) {
            session.setAttribute("userList", copyList);
        }

        //save last page access
        session.setAttribute("numPage", numPage);

        request.getRequestDispatcher(URL_PRODUCT_MANAGE).forward(request, response);

    }

    private List<User> searchingUsers(HttpServletRequest request, UserDAO dao) {
        if (request != null) {
            String seachValue = request.getParameter("searchValue");
            String searchCategory = request.getParameter("searchCriteria");
            if (seachValue != null && searchCategory != null) {
                if (!searchCategory.matches("address")) {
                    List<User> copyList = dao.getUsersByCategory(seachValue, searchCategory);
                    return copyList;
                } else {
                    AddressDAO addressDAO = new AddressDAO();
                    List<Integer> ids = addressDAO.getCustomerIdsByPartialAddress(seachValue);
                    return ids.stream().map(dao::getUserById).collect(Collectors.toList());
                }

            }
        }
        return null;

    }

    private void setDelete(List<User> list, int id, String status) {
        System.out.println(list);
        System.out.println(id);
        System.out.println(status);
        Optional<User> optinal = list.stream().filter(user -> user.getId() == id).findFirst();
        if (optinal.isPresent()) {
            optinal.get().setStatus(status);
            
        }
    }

    private List<User> findUserGroup(HttpServletRequest request, UserDAO dao) {
        if (request != null) {
            String searchCategory = request.getParameter("userGroup");
            if (searchCategory != null) {
                if (searchCategory.matches("all")) {
                    return dao.getAllUser();
                } else if (searchCategory.matches("banned")) {
                    return dao.getBannedUser();
                } else {
                    return dao.getWarningUser();
                }
            }
        }
        return null;
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
        UserDAO dao = new UserDAO();
        HttpSession session = request.getSession();
        List<User> list = (List<User>) session.getAttribute("userList");
//        if (list == null){
//            list = dao.getAllUser();
//        }
        try {
            String deleteId = request.getParameter("deleteUserId");
            if (deleteId != null && list != null) {
                String status = request.getParameter("status");
                setDelete(list, Integer.parseInt(deleteId), status);
                String refinedURl = DELETE_USER_URL + "&deleteUserId=" + deleteId + "&status=" + status;
                request.getRequestDispatcher(refinedURl).forward(request, response);
            } else {
                list = dao.getAllUser();
                session.setAttribute("userList", list);
                session.setAttribute("numPage", 1);

                request.getRequestDispatcher(URL_PRODUCT_MANAGE).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
