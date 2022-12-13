package bank.exceptions;

public class AccountDoesNotExistException extends Exception {
    /**
     * using constructor to create an exception object
     * @param Ausgabe
     */
    public AccountDoesNotExistException(String Ausgabe){
        super(Ausgabe);
    }
}
