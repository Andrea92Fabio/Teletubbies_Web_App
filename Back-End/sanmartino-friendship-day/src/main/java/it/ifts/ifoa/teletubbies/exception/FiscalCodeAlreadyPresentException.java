package it.ifts.ifoa.teletubbies.exception;

public class FiscalCodeAlreadyPresentException extends InvalidFiscalCodeException {
    public FiscalCodeAlreadyPresentException(String message) {
        super(message);
    }
}
