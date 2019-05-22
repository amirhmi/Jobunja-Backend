package Controller;

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
    public List<User.UserJson> getUsers(@RequestParam(value = "searchKey", required = false) String searchKey,
                                        @RequestAttribute int userId) {
        List<User> users = MiddlewareService.getUsersExceptCurrent(searchKey, userId);
        List<User.UserJson> usersRes = new ArrayList<>();
        for (User user : users)
            usersRes.add(user.toUserJson());
        return usersRes;
    }

    @GetMapping("/myid")
    public String getLoginUserId(@RequestAttribute int userId) {
        return Integer.toString(userId);
    }

    @GetMapping("/{id}")
    public User.UserJson getUser(@PathVariable(value = "id") String id, @RequestAttribute String userId) {
        User user = MiddlewareService.getSpecificUser(Integer.parseInt(id));
        if(user == null) {
            throw new CustomException.UserNotFoundException();
        }
        return user.toUserJson();
    }

    @GetMapping("/{id}/amiendorser")
    public List<Boolean> getIsEndorsedBefore(@PathVariable(value = "id") String id, @RequestAttribute int userId) {
        User user = MiddlewareService.getSpecificUser(Integer.parseInt(id));
        if (user == null){
            throw new CustomException.UserNotFoundException();
        }
        return user.getIsEndorser(MiddlewareService.getCurrentUser(userId).getId());
    }

    @PutMapping(value = "/{id}/endorse")
    public User.UserJson endorseSkill(@PathVariable(value = "id") String endorsedId,
                                        @RequestParam String skillName, @RequestAttribute int userId) {
        if (!MiddlewareService.userExists(Integer.parseInt(endorsedId)))
            throw new CustomException.UserNotFoundException();
        MiddlewareService.endorseSkillForOtherUser(skillName, Integer.parseInt(endorsedId), userId);
        return MiddlewareService.getSpecificUser(Integer.parseInt(endorsedId)).toUserJson();
    }

}
