package Controller;

import Model.Entity.DataBase;
import Model.Entity.User;
import Model.Service.MiddlewareService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("ShowUser")
public class ShowUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id != null && !id.isEmpty()) {
            User user = MiddlewareService.getSpecificUser(id);
            if(user != null) {
                if (user == DataBase.only_login_user)
                {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("Profile.jsp").forward(request, response);
                }
                else
                {
                    request.setAttribute("user", user);
                }
                return;
            }
        }
        response.setStatus(400);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Bad Request</h3>");
        out.println("<p>Please set proper project id in url</p>");
    }
}
