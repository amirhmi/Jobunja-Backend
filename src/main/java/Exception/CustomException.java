package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomException {

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
    public static class UserNotFoundException extends RuntimeException {
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
    public static class BadRequestException extends RuntimeException {
    }
}
