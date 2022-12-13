package bank.exceptions;

public class TransactionAttributeException extends Exception {
    public TransactionAttributeException(String output) {
        super(output);
    }
}
