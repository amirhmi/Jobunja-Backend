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

@WebServlet("/AddSkill")
public class AddSkill extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skill");
        if(skillName != null && !skillName.isEmpty()) {
            boolean status = MiddlewareService.addSkillForLoginUser(skillName);
            if(status == true) {
//                User currentUser = MiddlewareService.
                response.setStatus(200);
                request.setAttribute("message", "skill added successfully");
                request.setAttribute("user", );
            }
        }
    }
}
