package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException {

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
    public static class UserNotFoundException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Project not found")
    public static class ProjectNotFoundException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
    public static class BadRequestException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "Skill already exists")
    public static class SkillAlreadyExistsException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Skill name is not valid")
    public static class InvalidSkillNameException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "endorsed user cannot be same as login user.")
    public static class EndorseByOwnerException extends  RuntimeException { }

    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "selected user does not have requested skill")
    public static class SkillNotFoundForUserException extends RuntimeException { }

    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED, reason = "this skilled is already endorsed")
    public static class SkillAlreadyEndorsedException extends RuntimeException { }
}
