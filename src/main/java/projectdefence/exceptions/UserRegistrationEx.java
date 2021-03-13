package projectdefence.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Registration failed!")
public class UserRegistrationEx extends CustomEx {

    public UserRegistrationEx(String msg) {
        super(msg);
    }
}
