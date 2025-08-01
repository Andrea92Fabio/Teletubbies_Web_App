package it.ifts.ifoa.teletubbies.exception;

public class EmailAlreadyPresentException extends InvalidEmailException {
    public EmailAlreadyPresentException(String message) {
        super(message);
    }
}
