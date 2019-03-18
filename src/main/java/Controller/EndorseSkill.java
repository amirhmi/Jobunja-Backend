package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Exception.CustomException;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EndorseSkill {

    @RequestMapping(value = "/endorseSkill/{userid}/{skill}", method = RequestMethod.PUT)
    public Skill.SkillJson endorseSkill(@PathVariable(value = "userid") String userId, @PathVariable(value = "skill") String skillName) {
        User endorsedUser = MiddlewareService.getSpecificUser(userId);
        if (endorsedUser == null)
            throw new CustomException.UserNotFoundException();
        Skill skill = MiddlewareService.endorseSkillForOtherUser(skillName, endorsedUser);
        return skill.toSkillJson();
    }
}

