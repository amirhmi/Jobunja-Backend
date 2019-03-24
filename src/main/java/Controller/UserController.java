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

    @GetMapping("/myid")
    public String getLoginUserId() {
        return MiddlewareService.getCurrentUser().getId();
    }

    @GetMapping("/{id}")
    public User.UserJson getUser(@PathVariable(value = "id") String id) {
        User user = MiddlewareService.getSpecificUser(id);
        if(user == null) {
            throw new CustomException.UserNotFoundException();
        }
        return user.toUserJson();
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
