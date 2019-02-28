package Contoller;

import Model.Entity.DataBase;
import Model.Entity.Project;
import Model.Service.MiddlewareService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ShowProject")
public class ShowProject extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id != null && !id.isEmpty()) {
            List<Project> projectList = MiddlewareService.;
            request.setAttribute("projectList", projectList);
            request.getRequestDispatcher("ShowProjects.jsp").forward(request, response);
        }
        response.setStatus(400);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Bad Request</h3>");
        out.println("<p>Please set correct project id in url</p>");
    }
}
