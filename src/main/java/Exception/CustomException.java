package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException {

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
    public static class UserNotFoundException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Project not found")
    public static class ProjectNotFoundException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Skill already exists")
    public static class SkillAlreadyExistsException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Skill name is not valid")
    public static class InvalidSkillNameException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Project Already bid")
    public static class ProjectAlreadyBidExcption extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Project not suited for this user")
    public static class NotSuitedProjectBidException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Bad bid amount")
    public static class BadBidAmountException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "endorsed user cannot be same as login user.")
    public static class EndorseByOwnerException extends  RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "selected user does not have requested skill")
    public static class SkillNotFoundForUserException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "this skilled is already endorsed")
    public static class SkillAlreadyEndorsedException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "sql exception")
    public static class SqlException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "bad parameter for singup")
    public static class SignupException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "user name already exists")
    public static class AlreadyUserNameExist extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "user name already exists")
    public static class LoginException extends RuntimeException { }

}
