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

@WebServlet("/SetBid")
public class SetBid extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int bidAmount = Integer.parseInt(request.getParameter("bidAmount"));
            String id = request.getParameter("id");
            boolean status = MiddlewareService.setBid(id, bidAmount);
            if(!status)
                throw new NumberFormatException();
            request.setAttribute("message", "Bid set succesfully");
            request.getRequestDispatcher("ShowProject.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("message", "Error occurred while setting bid");
            request.getRequestDispatcher("ShowProject.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
