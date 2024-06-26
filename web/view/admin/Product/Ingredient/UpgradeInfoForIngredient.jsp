<%@page import="dto.product.Ingredient"%>
<%@page import="dao.product.IngredientDAO"%>

<%@page import="java.io.File"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Update Ingredient</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<%
    // Mock old ingredient data (in practice, retrieve this from the database)
    String url = "/AMainController?action=IngredientUpdate";
    IngredientDAO dao = new IngredientDAO();
    if (request.getParameter("id") == null){
        response.sendRedirect(request.getContextPath()+"/AMainController?action=ingredientManagePage");
        return;
    }
    int id = Integer.parseInt(request.getParameter("id"));
    Ingredient oldIngredient = dao.getIngredientFromId(id);
    
%>
<div class="container mt-5">
    <div class="row">
        <!-- Left side: Display old information -->
        <div class="col-md-6">
            <h3>Old Information</h3>
            <c:set var="item" value= "<%=oldIngredient%>" />
            <div class="card">
                <div class="card-body">
                    <p><strong>ID:</strong> ${item.getId()}</p>
                    <p><strong>Name:</strong> ${item.getName()}</p>
                    <p><strong>Price:</strong> ${item.getPrice()} USD</p>
                    <p><strong>Unit:</strong> ${item.getUnit()}</p>
                    <p><strong>Image:</strong></p>
                    <img src="${pageContext.request.contextPath}/${item.getImgURL()}" alt="Old Image" class="img-fluid">
                </div>
            </div>
        </div>
        
        <!-- Right side: Form to input new values -->
        <div class="col-md-6">
            <h3>Update Information</h3>
            <form action="<%=request.getContextPath()+url%>&id=${item.getId()}" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="ingredientName">Name</label>
                    <input type="text" class="form-control" id="ingredientName" name="ingredientName" required>
                </div>
                <div class="form-group">
                    <label for="price">Price (USD)</label>
                    <input type="number" step="0.01" class="form-control" id="price" name="price" required>
                </div>
                <div class="form-group">
                    <label for="unit">Unit</label>
                    <input type="text" class="form-control" id="unit" name="unit" required>
                </div>
                <div class="form-group">
                    <label for="imgURL">Image</label>
                    <input type="file" class="form-control-file" id="imgURL" name="imgURL" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Update Ingredient</button>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS and dependencies -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>