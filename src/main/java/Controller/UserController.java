package Controller;

import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.*;
import Exception.CustomException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User.UserJson> getUsers() {
        List<User> users = MiddlewareService.getUsersExceptCurrent();
        List<User.UserJson> usersRes = new ArrayList<>();
        for (User user : users)
            usersRes.add(user.toUserJson());
        return usersRes;
    }

    @GetMapping("/{id}")
    public User.UserJson getUser(@PathVariable(value = "id") String id) {
        User user = MiddlewareService.getSpecificUser(id);
        if(user == null) {
            throw new CustomException.UserNotFoundException();
        }
        return user.toUserJson();
    }

    @PutMapping("/{id}/skills")
    public List<User.UserJson.SkillJson> addSkill(@PathVariable(value="id") String userId,
                                                  @RequestBody Skill skill) {
        MiddlewareService.addSkillForLoginUser(skill.getName());
        return MiddlewareService.getCurrentUser().toUserJson().skills;
    }

    @DeleteMapping("/{id}/skills")
    public List<User.UserJson.SkillJson> removeSkill(@PathVariable(value="id") String userId,
                                                     @RequestBody Skill skill) {
        MiddlewareService.RemoveSkillForLoginUser(skill.getName());
        return MiddlewareService.getCurrentUser().toUserJson().skills;
    }

    @PutMapping(value = "/{id}/endorse")
    public Skill.SkillJson endorseSkill(@PathVariable(value = "id") String userId,
                                        @RequestBody Skill skill) {
        User endorsedUser = MiddlewareService.getSpecificUser(userId);
        if (endorsedUser == null)
            throw new CustomException.UserNotFoundException();
        Skill skillRes = MiddlewareService.endorseSkillForOtherUser(skill.getName(), endorsedUser);
        return skillRes.toSkillJson();
    }

}
