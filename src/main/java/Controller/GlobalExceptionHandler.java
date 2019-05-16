package Controller;

import Exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private String messageToJson(String message)
    {
        return "{\"message\": \"" + message + "\"}";
    }

    @ExceptionHandler({CustomException.UserNotFoundException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.UserNotFoundException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("User not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomException.ProjectNotFoundException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.ProjectNotFoundException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("Project not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CustomException.SkillAlreadyExistsException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.SkillAlreadyExistsException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("Skill already exists"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.InvalidSkillNameException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.InvalidSkillNameException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("Skill name is not valid"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.ProjectAlreadyBidExcption.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.ProjectAlreadyBidExcption exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("Project Already bid"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.NotSuitedProjectBidException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.NotSuitedProjectBidException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("Project not suited for this user"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.BadBidAmountException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.BadBidAmountException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("Bad bid amount"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.EndorseByOwnerException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.EndorseByOwnerException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("endorsed user cannot be same as login user"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.SkillNotFoundForUserException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.SkillNotFoundForUserException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("selected user does not have requested skill"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.SkillAlreadyEndorsedException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.SkillAlreadyEndorsedException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("this skilled is already endorsed"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.SignupException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.SignupException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("this skilled is already endorsed"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({CustomException.AlreadyUserNameExist.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.AlreadyUserNameExist exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("this skilled is already endorsed"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, SQLException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("error in getting valid skills"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CustomException.LoginException.class})
    public ResponseEntity<String> handleException(HttpServletRequest req, CustomException.LoginException exception, HttpServletResponse resp)
    {
        return new ResponseEntity<>(messageToJson("error in getting valid skills"), HttpStatus.FORBIDDEN);
    }
}
