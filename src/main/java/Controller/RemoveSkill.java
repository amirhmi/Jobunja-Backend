package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class RemoveSkill {

    @RequestMapping(value = "/removeSkill/{skill}", method = RequestMethod.DELETE)
    public List<Skill> removeSkill(@PathVariable(value = "userid") String userId, @PathVariable(value = "skill") String skillName) {
        MiddlewareService.RemoveSkillForLoginUser(skillName);
        return MiddlewareService.getCurrentUser().getSkills();
    }
}
