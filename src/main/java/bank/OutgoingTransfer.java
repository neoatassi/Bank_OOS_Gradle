package bank;

import bank.exceptions.AmountNotValidException;

public class OutgoingTransfer extends Transfer{

    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) throws AmountNotValidException {
        super(date, amount, description, sender, recipient);
    };

    public OutgoingTransfer(Transfer transfer) throws AmountNotValidException {
        super(transfer);
    }

    public OutgoingTransfer(String date, double amount, String description) throws AmountNotValidException {
        super(date, amount, description);
    }

    @Override
    public double calculate(){
        return (-1 * super.calculate());
    }
}