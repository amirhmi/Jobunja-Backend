package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

@WebServlet("/AddSkill")
public class AddSkill extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skill");
        User currentUser = MiddlewareService.getCurrentUser();
        request.setAttribute("user", currentUser);
        if(skillName != null && !skillName.isEmpty()) {
            boolean status = MiddlewareService.addSkillForLoginUser(skillName);
            if(status == true) {
                response.setStatus(200);
                List<String> skillNames = MiddlewareService.CanBeAddedSkills();
                request.setAttribute("skillNames", skillNames);
                request.setAttribute("message", "skill removed successfully");
                request.setAttribute("message", "skill added successfully");
                request.getRequestDispatcher("Profile.jsp").forward(request, response);
                return;
            }
        }
        List<String> skillNames = MiddlewareService.CanBeAddedSkills();
        request.setAttribute("skillNames", skillNames);
        response.setStatus(400);
        request.setAttribute("message", "Please select proper skill");
        request.getRequestDispatcher("Profile.jsp").forward(request, response);
    }
}
