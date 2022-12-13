package bank.exceptions;

public class AmountNotValidException extends Exception{
    public AmountNotValidException(String output) {
        super(output);
    }
}
