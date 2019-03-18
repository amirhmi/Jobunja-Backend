package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AddSkill {

    @RequestMapping(value = "/addSkill", method = RequestMethod.PUT)
    public List<Skill> addSkill(HttpServletRequest request,
                                @RequestParam(value="skill", required=true) String skillName) {
        MiddlewareService.addSkillForLoginUser(skillName);
        User currentUser = MiddlewareService.getCurrentUser();
        List<Skill> skills = currentUser.getSkills();
        return skills;
    }
}
