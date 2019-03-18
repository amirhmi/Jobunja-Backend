package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Exception.CustomException;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EndorseSkill {

    @RequestMapping(value = "/endorseSkill/{userid}", method = RequestMethod.PUT)
    public List<Skill> endorseSkill(@PathVariable(value = "userid") String userId,
                                    HttpServletRequest request,
                                    @RequestParam(value="skill", required=false) String skillName) {
        User endorsedUser = MiddlewareService.getSpecificUser(userId);
        if (endorsedUser == null)
            throw new CustomException.UserNotFoundException();
        MiddlewareService.endorseSkillForOtherUser(skillName, endorsedUser);
        return endorsedUser.getSkills();
    }
}

