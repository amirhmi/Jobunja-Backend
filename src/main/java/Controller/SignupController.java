package Controller;

import DataAccess.UserDataMapper;
import Model.Entity.User;
import Model.Service.Cryptography;
import Model.Service.MiddlewareService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Exception.CustomException;

@RestController
@RequestMapping("/signup")
public class SignupController {
    @PostMapping
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

    private class SignupUserModel {
        private String firstName;
        private String lastName;
        private String userName;
        private String jobTitle;
        private String imgUrl;
        private String bio;

        public String getFirstName() {
            return firstName;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public String getUserName() {
            return userName;
        }
        public void setUserName(String userName) {
            this.userName = userName;
        }
        public String getJobTitle() {
            return jobTitle;
        }
        public void setJobTitle(String jobTitle) {
            this.jobTitle = jobTitle;
        }
        public String getImgUrl() {
            return imgUrl;
        }
        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
        public String getBio() {
            return bio;
        }
        public void setBio(String bio) {
            this.bio = bio;
        }
    }
}
