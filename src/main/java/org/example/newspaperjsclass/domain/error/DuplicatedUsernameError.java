package org.example.newspaperjsclass.domain.error;

public class DuplicatedUsernameError extends RuntimeException {
    public DuplicatedUsernameError(String message) {
        super(message);
    }
}
