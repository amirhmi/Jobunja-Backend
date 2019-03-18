package Controller;

import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndorseSkill {

    @RequestMapping(value = "/endorseSkill/{userid}/{skill}", method = RequestMethod.PUT)
    public String endorseSkill(@PathVariable(value = "userid") String userId, @PathVariable(value = "skill") String skillName) {
        User endorsedUser = MiddlewareService.getSpecificUser(userId);
        if(skillName != null && !skillName.isEmpty()) {
            MiddlewareService.endorseSkillForOtherUser(skillName, endorsedUser);
            String response = "Skill endorsed successfully";
            return response;
        }
    }
}

