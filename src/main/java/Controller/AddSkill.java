package Controller;

import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddSkill {

    @RequestMapping(value = "/addSkill/{skill}", method = RequestMethod.PUT)
    public String addSkill(@PathVariable(value = "skill") String skillName) {
        MiddlewareService.addSkillForLoginUser(skillName);
        String response = "Skill " + skillName + " added succesfuly";
        return response;
    }
}
