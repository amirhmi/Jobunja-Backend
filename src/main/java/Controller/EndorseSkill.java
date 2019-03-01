package Controller;

import Model.Entity.User;
import Model.Service.MiddlewareService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EndorseSkill")
public class EndorseSkill extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String skillName = request.getParameter("skill");
        String userId = request.getParameter("userId");
        User otherUser = MiddlewareService.getSpecificUser(userId);
        request.setAttribute("user", otherUser);
        if(skillName != null && !skillName.isEmpty()) {
            boolean status = MiddlewareService.endorseSkillForOtherUser(skillName, otherUser);
            if(status) {
                response.setStatus(200);
                request.setAttribute("message", "skill endorsed successfully");
                request.getRequestDispatcher("UserInfo.jsp").forward(request, response);
                return;
            }
        }
        response.setStatus(400);
        request.setAttribute("message", "Please select proper skill");
        request.getRequestDispatcher("UserInfo.jsp").forward(request, response);
    }
}
