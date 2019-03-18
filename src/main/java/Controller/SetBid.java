package Controller;

import Model.Entity.DataBase;
import Model.Entity.Project;
import Model.Service.MiddlewareService;
import org.apache.http.annotation.Contract;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class SetBid extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bidAmount = Integer.parseInt(request.getParameter("bidAmount"));
            String id = request.getParameter("id");
            boolean status = MiddlewareService.setBid(id, bidAmount);
            if(!status)
                throw new NumberFormatException();
            Project project = MiddlewareService.getSpecificProject(id);
            request.setAttribute("project", project);
            request.setAttribute("showBid", false);
            request.setAttribute("message", "Bid set succesfully");
            request.getRequestDispatcher("ShowProject.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            String id = request.getParameter("id");
            Project project = MiddlewareService.getSpecificProject(id);
            if(project != null)
                request.setAttribute("project", project);
            boolean hasBid = MiddlewareService.hasCurrentUserBidForProject(project);
            request.setAttribute("showBid", !hasBid);
            request.setAttribute("message", "Error occurred while setting bid");
            request.getRequestDispatcher("ShowProject.jsp").forward(request, response);
        }
    }
}
