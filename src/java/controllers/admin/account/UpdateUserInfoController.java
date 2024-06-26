import dao.account.UserDAO;
import dto.account.Address;
import dto.account.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UpdateUserInfoController", urlPatterns = {"/admin/user/UpdateUserInfoController"})
public class UpdateUserInfoController extends HttpServlet {

    private static final String IMAGES_DIRECTORY = "images/customer/";
    private static final String SUCCESS_URL = "/AMainController?action=userDetail";
    private static final String ERROR_URL = "/AMainController?action=error";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String currentProjectPath = getServletContext().getRealPath("/");
        String uploadPath = currentProjectPath.concat(IMAGES_DIRECTORY);

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        int id = 0;
        String name = null;
        String email = null;
        String phone = null;
        String city = null;
        String district = null;
        String ward = null;
        String street = null;
        String imgURL = null;
        UserDAO dao = new UserDAO();

        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            diskFileItemFactory.setRepository(new File(currentProjectPath.concat("web")));
            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
            List<FileItem> fileItems = fileUpload.parseRequest(request);

            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    // Handle regular form fields
                    switch (fileItem.getFieldName()) {
                        case "id":
                            id = Integer.parseInt(fileItem.getString());
                            break;
                        case "name":
                            name = fileItem.getString();
                            break;
                        case "email":
                            email = fileItem.getString();
                            break;
                        case "phone":
                            phone = fileItem.getString();
                            break;
                        case "city":
                            city = fileItem.getString();
                            break;
                        case "district":
                            district = fileItem.getString();
                            break;
                        case "ward":
                            ward = fileItem.getString();
                            break;
                        case "street":
                            street = fileItem.getString();
                            break;
                        default:
                            break;
                    }
                } else {
                    // Handle file uploads
                    if (fileItem.getFieldName().equals("imgURL")) {
                        String fileName = Paths.get(fileItem.getName()).getFileName().toString();
                        String filePath = uploadPath + fileName;
                        System.out.println(filePath);
                        File file = new File(filePath);
                        fileItem.write(file);
                        imgURL = IMAGES_DIRECTORY + fileName;
                    }
                }
            }
            Address address = null;
            if (city!= null && ward!= null &&district!= null && street!= null){
                address = new Address(city, district, ward, street, id);
            }

            User oldUser = dao.getUserById(id);
            // Save the user data to the database
            User user = new User(id, email, oldUser.getPw(), name,address, phone, imgURL, oldUser.getStatus());
            dao.updateUser(user);

            request.getRequestDispatcher(SUCCESS_URL + "&userId=" + user.getId()).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher(ERROR_URL).forward(request, response);
        }
    }
}
