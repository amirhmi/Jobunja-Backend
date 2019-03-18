package Controller;

import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShowUsers {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User.UserJson> showUsers() {
        List<User> response = MiddlewareService.getUsersExceptCurrent();
        List<User.UserJson> ret = new ArrayList<>();
        for (User user : response)
            ret.add(user.toUserJson());
        return ret;
    }
}
