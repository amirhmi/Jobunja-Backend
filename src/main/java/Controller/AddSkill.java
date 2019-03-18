package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddSkill {

    @RequestMapping(value = "/addSkill/{skill}", method = RequestMethod.PUT)
    public List<Skill> addSkill(@PathVariable(value = "skill") String skillName) {
        MiddlewareService.addSkillForLoginUser(skillName);
        User currentUser = MiddlewareService.getCurrentUser();
        List<Skill> skills = currentUser.getSkills();
        return skills;
    }
}
