package Controller;

import DataAccess.UserDataMapper;
import Model.Entity.User;
import Model.Service.Cryptography;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import Exception.CustomException;


@RestController
public class AuthController {
    @PostMapping("/signup")
    public void signup(String firstName, String lastName, String userName, String password,
                       String jobTitle, String imgUrl, String bio) {
        if(firstName.length() == 0 || lastName.length() == 0 || userName.length() == 0 ||
           password.length() < 6 || jobTitle.length() == 0 || imgUrl.length() == 0)
            throw new CustomException.SignupException();
        if(UserDataMapper.existUserName(userName))
            throw new CustomException.AlreadyUserNameExist();

        String hashedPassword = Cryptography.getSHA(password);
        User newUser = new User(firstName, lastName, userName, hashedPassword, jobTitle, imgUrl, bio);
        MiddlewareService.addUser(newUser);
    }

    @PostMapping("/login")
    public String login(String userName, String password) {
        String hashedPassword = Cryptography.getSHA(password);
        System.out.println(userName + ' ' + password);
        int userId = MiddlewareService.findByUsernamePassword(userName, hashedPassword);
        System.out.println(userId);
        if(userId == 0) {
            throw new CustomException.LoginException();
        }
        return Cryptography.createJWT(Integer.toString(userId), "jobunja", 1800000);
    }
}
