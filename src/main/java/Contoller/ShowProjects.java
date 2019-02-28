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
import java.util.List;

@WebServlet("/project")
public class ShowProjects extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Project> projectList = MiddlewareService.getSuitedProjects(DataBase.only_login_user);
        request.setAttribute("projectList", projectList);
        request.getRequestDispatcher("ShowProjects.jsp").forward(request, response);
    }
}
