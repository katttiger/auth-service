package se.iths.cecilia.authservice.exceptions;

/***
 * Custom error to handle if a user is not in the database.
 * */

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
