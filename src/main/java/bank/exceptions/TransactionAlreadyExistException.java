package bank.exceptions;

public class TransactionAlreadyExistException extends Exception {
    public TransactionAlreadyExistException(String output) {
        super(output);
    }
}
