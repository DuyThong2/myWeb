/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class AMainController extends HttpServlet {

    private final String SUCCESS = "view/success.jsp";
    private final String ERROR = "view/error.jsp";
    private final String REGISTER_FORM = "RegisterForm.jsp";
    private final String LOGIN = "LoginServlet";
    private final String REGISTER = "RegisterServlet";
    private final String USER_MAINPAGE = "view/user/mainPage.jsp";
    private final String ADMIN_MAINPAGE = "view/admin/AdminMainPage.jsp";

    //Ingredient
    private final String INGREDIENT_MANAGE = "view/admin/Product/Ingredient/IManage.jsp";
    private final String INGREDIENT_MANAGE_CONTROLLER = "/admin/ingredient/manage";

    private final String INGREDIENT_INSERT = "view/admin/Product/Ingredient/InsertI.jsp";
    private final String INGREDIENT_INSERT_CONTROLLER = "/admin/ingredient/insertIngredient";

    private final String INGREDIENT_UPDATE = "view/admin/Product/Ingredient/UpgradeInfoForIngredient.jsp";
    private final String INGREDIENT_UPDATE_CONTROLLER = "/admin/ingredient/UpdateIngredient";

    //MEAL 
    private final String MEAL_DETAIL = "view/admin/Product/Meal/MealDetail.jsp";

    private final String MEAL_MANAGE = "view/admin/Product/Meal/MManage.jsp";
    private final String MEAL_MANAGE_CONTROLLER = "/admin/meal/MealManagement";

    private final String MEAL_INSERT = "view/admin/Product/Meal/InsertM.jsp";
    private final String MEAL_INSERT_CONTROLLER = "/admin/meal/MealInsertController";

    private final String MEAL_UPDATE = "view/admin/Product/Meal/UpdateInfoForMeal1.jsp";
    private final String MEAL_UPDATE_CONTROLLER = "/admin/meal/UpdateMealInfoController";

    private final String PRODUCT_DELETE = "";
    private final String PRODUCT_DELETE_CONTROLLER = "/admin/DeleteMealController";

    //MEAL PLAN
    private final String MEAL_PLAN_PAGE = "view/admin/Product/MealPlan/MealPlanManage.jsp";
    private final String MEAL_PLAN_CONTROLLER = "/admin/MealPlanManageController";

    private final String MEAL_PLAN_DETAIL_PAGE = "view/admin/Product/MealPlan/MealPlanDetail.jsp";
    private final String MEAL_PLAN_DETAIL_CONTROLLER = "/admin/MealPlanDetailController";

    private final String MEAL_PLAN_INSERT_CONTROLLER = "/admin/MealPlanInsertController";
    private final String MEAL_PLAN_INSERT_PAGE = "view/admin/Product/MealPlan/MealPlanInsert.jsp";

    private final String MEAL_PLAN_UPDATE_CONTROLLER = "/admin/MealPlanUpdateController";
    private final String MEAL_PLAN_UPDATE_PAGE = "view/admin/Product/MealPlan/MealPlanUpdate.jsp";
    //DAY PLAN

    private final String DAY_PLAN_INSERT_PAGE = "view/admin/Product/DayPlan/DayPlanInsert.jsp";
    private final String DAY_PLAN_INSERT_CONTROLLER = "/admin/DayPlanInsertController";
    //PACKET
    private final String PACKET_INSERT = "view/admin/Product/Packet/InsertIngredientPacket.jsp";
    private final String PACKET_INSERT_CONTROLLER = "/admin/packet/InsertIngredientPacketController";

    private final String PACKET_UPDATE = "view/admin/Product/Packet/UpdateIngredientPacket.jsp";
    private final String PACKET_UPDATE_CONTROLLER = "/admin/packet/UpdateIngredientPacketController";

    //USER
    private final String USER_MANAGE = "view/admin/Account/UserManage.jsp";
    private final String USER_MANAGE_CONTROLLER = "/admin/account/UserManageController";

    private final String USER_DETAIL = "view/admin/Account/UserDetail.jsp";
    private final String USER_DETAIL_CONTROLLER = "/admin/account/UserDetail";

    private final String USER_DELETE = "";
    private final String USER_DELETE_CONTROLLER = "/admin/account/DeleteUser";

    private final String USER_UPDATE = "view/admin/Account/UpdateUser.jsp";
    private final String USER_UPDATE_CONTROLLER = "/admin/user/UpdateUserInfoController";

    //ORDER
    private final String ORDER_MANAGE = "view/admin/Order/OrderManage.jsp";
    private final String ORDER_MANAGE_CONTROLLER = "/admin/order/orderManage";

    private final String ORDER_DETAIL = "view/admin/Order/OrderDetail.jsp";
    private final String ORDER_DETAIL_CONTROLLER = "/admin/order/OrderDetail";

    private final String ORDER_UPDATE = "";
    private final String ORDER_UPDATE_CONTROLLER = "/admin/order/OrderStatusUpdate";

    //SALE
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            action = "";

        }
        String url = "";

        switch (action) {

            case "error":
                url = ERROR;
                break;
            case "success":
                url = SUCCESS;
                break;
            //Ingredient
            case "ingredientManagePage":
                url = INGREDIENT_MANAGE;
                break;
            case "ingredientManage":
                url = INGREDIENT_MANAGE_CONTROLLER;
                break;

            case "IngredientInsertPage":
                url = INGREDIENT_INSERT;
                break;
            case "IngredientInsert":
                url = INGREDIENT_INSERT_CONTROLLER;
                break;

            case "IngredientUpdatePage":
                url = INGREDIENT_UPDATE;
                break;
            case "IngredientUpdate":
                url = INGREDIENT_UPDATE_CONTROLLER;
                break;

            //Meal
            case "MealDetail":
                url = MEAL_DETAIL;
                break;

            case "MealManagePage":
                url = MEAL_MANAGE;
                break;
            case "MealManage":
                url = MEAL_MANAGE_CONTROLLER;
                break;

            case "MealInsertPage":
                url = MEAL_INSERT;
                break;
            case "MealInsert":
                url = MEAL_INSERT_CONTROLLER;
                break;

            case "MealUpdatePage":
                url = MEAL_UPDATE;
                break;
            case "MealUpdate":
                url = MEAL_UPDATE_CONTROLLER;
                break;

            //IngredientPacket
            case "PacketInsertPage":
                url = PACKET_INSERT;
                break;
            case "PacketInsert":
                url = PACKET_INSERT_CONTROLLER;
                break;

            case "PacketUpdatePage":
                url = PACKET_UPDATE;
                break;
            case "PacketUpdate":
                url = PACKET_UPDATE_CONTROLLER;
                break;
            //Meal Plan
            case "MealPlan":
                url = MEAL_PLAN_CONTROLLER;
                break;
            case "MealPlanPage":
                url = MEAL_PLAN_PAGE;
                break;

            case "MealPlanDetail":
                url = MEAL_PLAN_DETAIL_CONTROLLER;
                break;
            case "MainPlanDetailPage":
                url = MEAL_PLAN_DETAIL_PAGE;
                break;

            case "MealPlanInsert":
                url = MEAL_PLAN_INSERT_CONTROLLER;
                break;
            case "MealPlanInsertPage":
                url = MEAL_PLAN_INSERT_PAGE;
                break;

            case "MealPlanUpdate":
                url = MEAL_PLAN_UPDATE_CONTROLLER;
                break;
            case "MealPlanUpdatePage":
                url = MEAL_PLAN_UPDATE_PAGE;
                break;

            //DayPlan
            case "DayPlanInsert":
                url = DAY_PLAN_INSERT_CONTROLLER;
                break;
            case "DayPlanInsertPage":
                url = DAY_PLAN_INSERT_PAGE;
                break;
            //product
            case "ProductDelete":
                url = PRODUCT_DELETE_CONTROLLER;
                break;

            //sale
            //user
            case "userManagePage":
                url = USER_MANAGE;
                break;
            case "userManage":
                url = USER_MANAGE_CONTROLLER;
                break;

            case "deleteUser":
                url = USER_DELETE_CONTROLLER;
                break;

            case "userUpdatePage":
                url = USER_UPDATE;
                break;
            case "userUpdate":
                url = USER_UPDATE_CONTROLLER;
                break;

            case "userDetailPage":
                url = USER_DETAIL;
                break;
            case "userDetail":
                url = USER_DETAIL_CONTROLLER;
                break;
            //order
            case "orderManagePage":
                url = ORDER_MANAGE;
                break;
            case "orderManage":
                url = ORDER_MANAGE_CONTROLLER;
                break;

            case "orderDetailPage":
                url = ORDER_DETAIL;
                break;
            case "orderDetail":
                url = ORDER_DETAIL_CONTROLLER;
                break;

            case "updateOrderStatus":
                url = ORDER_UPDATE_CONTROLLER;
                break;

            case "insertOrder":
                break;

            //REGISTER 
            case "mainpage":
                url = USER_MAINPAGE;
                break;
            case "registerform":
                url = REGISTER_FORM;
                break;
            case "register":
                url = REGISTER;
                break;
            case "login":
                url = LOGIN;
                break;
            case "adminmainpage":
                url = ADMIN_MAINPAGE;
                break;

            default:
                url = "index.jsp";
                break;
        }

        request.getRequestDispatcher(url).forward(request, response);

        return;

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
