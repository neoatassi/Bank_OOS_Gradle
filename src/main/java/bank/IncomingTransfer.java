package bank;

import bank.exceptions.AmountNotValidException;

public class IncomingTransfer extends Transfer {

    public IncomingTransfer(String date, double amount, String description, String sender, String recipient) throws AmountNotValidException {
        super(date, amount, description, sender, recipient);
    };

    public IncomingTransfer(Transfer transfer) throws AmountNotValidException {
        super(transfer);
    }

    public IncomingTransfer(String date, double amount, String description) throws AmountNotValidException {
        super(date, amount, description);
    }

    @Override
    public double calculate() {
        return super.calculate();
    }

}
