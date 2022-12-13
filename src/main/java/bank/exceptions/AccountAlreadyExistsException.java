package bank.exceptions;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String output) {
        super(output);
    }
}
