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

        } catch (NumberFormatException e) {
            response.setStatus(400);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h3>Bad Request</h3>");
            out.println("<p>Please set proper BidAmount id in url</p>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
