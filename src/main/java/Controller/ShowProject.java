package Controller;

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

@WebServlet("/project")
public class ShowProject extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id != null && !id.isEmpty()) {
            Project project = MiddlewareService.getSpecificProject(id);
            if(project != null) {
                boolean hasBid = MiddlewareService.hasCurrentUserBidForProject(project);
                request.setAttribute("showBid", !hasBid);
                request.setAttribute("project", project);
                request.getRequestDispatcher("ShowProject.jsp").forward(request, response);
            }
        }
        System.out.println("salam");
        response.setStatus(400);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Bad Request</h3>");
        out.println("<p>Please set proper project id in url</p>");
    }
}


