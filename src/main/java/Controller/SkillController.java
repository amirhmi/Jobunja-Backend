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
    public List<String> getAddableSkills()
    {
        return MiddlewareService.CanBeAddedSkills();
    }

    @PutMapping
    public List<User.UserJson.SkillJson> addSkill(@RequestBody String skillName) {
        MiddlewareService.addSkillForLoginUser(skillName);
        return MiddlewareService.getCurrentUser().toUserJson().skills;
    }

    @DeleteMapping
    public List<User.UserJson.SkillJson> removeSkill(@RequestBody String skillName) {
        MiddlewareService.RemoveSkillForLoginUser(skillName);
        return MiddlewareService.getCurrentUser().toUserJson().skills;
    }
}
