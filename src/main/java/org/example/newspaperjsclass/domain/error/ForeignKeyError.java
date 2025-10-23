package org.example.newspaperjsclass.domain.error;

public class ForeignKeyError extends DatabaseError {
    public ForeignKeyError(String message) {
        super(message);
    }
}
