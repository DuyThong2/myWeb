<%@page import="dao.order.OrderItemDAO"%>
<%@page import="dao.order.OrderDAO"%>
<%@page import="dto.order.OrderItem"%>
<%@page import="java.util.HashMap"%>
<%@page import="dto.order.Order"%>
<%@page import="java.util.Map"%>
<%@page import="dto.product.Product"%>
<%@page import="dto.account.User"%>
<%@page import="dao.account.UserDAO"%>
<%@page import="java.util.List"%>
<%@page import="dao.discount.DiscountDAO"%>
<%@page import="dto.product.Meal"%>
<%@page import="dao.product.MealDAO"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import="dto.product.IngredientPacket"%>
<%@page import="dto.product.Ingredient"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Details</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    </head>

    <%
        String updateOrderStatusURL = request.getContextPath() + "/MainController?action=updateUserPage";
        String redirectURL = request.getContextPath() + "/MainController?action=userDetailPage";
        List<Meal> list = (List<Meal>) session.getAttribute("mealList");
        if (list == null) {
            // only for testing
            UserDAO userDao = new UserDAO();
            User user = userDao.getUserById(1);
            session.setAttribute("user", user);
            MealDAO mealDAO = new MealDAO();
            list = mealDAO.getCustomerMealList();
            session.setAttribute("mealList", list);
            Map<Product, Integer> cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null){
            response.sendRedirect(redirectURL);
            return;
        }
        
        OrderDAO orderDAO= new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        int orderid = Integer.parseInt(orderIdStr);
        Order order = orderDAO.getOrderByOrderId(orderid);
        
        List<OrderItem> orderItems = orderItemDAO.getOrderDetails(orderid);
        request.setAttribute("orderItems", orderItems);
       

        
    %>
    <body>

        <div class="container-fluid">
            <!-- First Row: Order Information -->
            <div class="row mt-4">
                <div class="col-lg-8">
                    <h2>Order Information</h2>
                    <table class="table table-bordered">
                        <thead class="thead-light">
                            <tr>
                                <th>Order ID</th>
                                <th>Order Date</th>
                                <th>Checking Date</th>
                                <th>Abort Date</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><%= order != null ? order.getOrderID() : "N/A"%></td>
                                <td><%= order != null ? order.getOrderDate() : "N/A"%></td>
                                <td><%= order != null ? order.getCheckingDate() : "N/A"%></td>
                                <td><%= order != null ? order.getAbortDate() : "N/A"%></td>
                                <td><%= order != null ? order.getStatus() : "N/A"%></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

                <div class="col-lg-4">
                    <div class="btn-group">
                        <c:choose>
                            <c:when test="${order.status == 'processing'}">
                                <a href="<%=updateOrderStatusURL%>&orderId=${order.orderID}&status=abort" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Abort</a>
                            </c:when>
                            <c:when test="${order.status == 'pending'}">
                                <a href="<%=updateOrderStatusURL%>&orderId=${order.orderID}&status=abort" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Abort</a>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>

            <!-- Second Row: Order Items and Customer Info -->
            <div class="row mt-4">
                <div class="col-lg-8">
                    <h2>Order Items</h2>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead class="thead-light">
                                <tr>
                                    <th>Image</th>
                                    <th>Product Name</th>
                                    <th>Description</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${not empty orderItems}">
                                    <c:forEach var="item" items="${orderItems}">
                                        <tr>
                                            <td>
                                                <img src="${pageContext.request.contextPath}/${item.product.getImageURL()}" alt="${item.product.name}" width="100" height="100">
                                            </td>
                                            <td>${item.product.name}</td>
                                            <td>${item.product.description}</td>
                                            <td>${item.getQuantity()}</td>
                                            <td>${item.getPrice()*item.getQuantity()}</td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>