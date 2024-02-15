package miniproject.stellanex.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "Email duplicated."),
    EMAIL_NOTFOUND(HttpStatus.NOT_FOUND, "Email not found."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Password invalid");

    private HttpStatus httpStatus;
    private String message;

}
