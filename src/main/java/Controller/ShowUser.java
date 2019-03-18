package Controller;

import Model.Entity.DataBase;
import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import Exception.CustomException;

@RestController

public class ShowUser {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User showUser(@PathVariable(value = "id") String id) {
        if (id == null || id.isEmpty()) {
            throw new CustomException.BadRequestException();
        }

        User user = MiddlewareService.getSpecificUser(id);
        if(user == null) {
            throw new CustomException.UserNotFoundException();
        }

        User response = MiddlewareService.getSpecificUser(id);
        return response;
    }
}
