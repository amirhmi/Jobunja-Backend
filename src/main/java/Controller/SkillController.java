package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {
    @GetMapping
    public List<String> getAddableSkills(@RequestAttribute int userId)
    {
        return MiddlewareService.CanBeAddedSkills(userId);
    }

    @PutMapping
    public User.UserJson addSkill(@RequestParam String skillName, @RequestAttribute int userId) {
        MiddlewareService.addSkillForLoginUser(skillName, userId);
        return MiddlewareService.getCurrentUser(userId).toUserJson();
    }

    @DeleteMapping
    public User.UserJson removeSkill(@RequestParam String skillName, @RequestAttribute int userId) {
        MiddlewareService.RemoveSkillForLoginUser(skillName, userId);
        return MiddlewareService.getCurrentUser(userId).toUserJson();
    }
}
