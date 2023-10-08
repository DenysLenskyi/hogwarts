package ua.foxminded.javaspring.lenskyi.university.exception;

public class NotUniqueUsernameException extends RuntimeException {

    public NotUniqueUsernameException(String message) {
        super(message);
    }
}
