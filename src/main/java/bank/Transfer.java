package bank;

import bank.exceptions.AmountNotValidException;

public class Transfer extends Transaction {
    /**
     * ueberweisender Aktuer
     */
    protected String sender;
/**
     * empfangener Aktuer
     */
    protected String recipient;

    public Transfer(String date, double amount, String description) throws AmountNotValidException {
        super(date, amount, description);
    }

    public Transfer(String date, double amount, String description, String sender, String recipient) throws AmountNotValidException {
        this(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }

    /**
     * Copy constructor
     * @param transfer
     */
    public Transfer(Transfer transfer) throws AmountNotValidException{
        this(transfer.getDate(),
                transfer.getAmount(),
                transfer.getDescription(),
                transfer.getSender(),
                transfer.getRecipient()
        );
    }

    public String getDate() {
        return super.getDate();
    }

    public void setDate(String date) {
        super.setDate(date);
    }

    public double getAmount() {
        return super.getAmount();
    }

    /**
     * nur positive Werte erlaubt
     * @param amount
     */
    @Override
    public void setAmount(double amount) throws AmountNotValidException {
        if (amount >= 0) this.amount = amount;
        else throw new AmountNotValidException("Amount not valid! Only positive values");
    }

    public String getDescription() {
        return super.getDescription();
    }

    public void setDescription(String description) {
        super.setDescription(description);
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public double calculate(){
        return getAmount();
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nSender: " + getSender() +
                "\nRecipient: "  + getRecipient();
    }

    @Override
    public boolean equals(Object obj){

        if(!super.equals(obj)) return false;

        Transfer temp = (Transfer) obj;

        return temp.getAmount() == this.getAmount() &&
                temp.getSender() == this.getSender() &&
                temp.getRecipient() == this.getRecipient();

    }

    /**
     * gibt werte alle Klassenattribute aus
     */
    public void printObject() throws AmountNotValidException {
        System.out.println("date = " + date);
        System.out.println("amount = " + amount);
        System.out.println("description = " + description);
        System.out.println("sender = " + sender);
        System.out.println("recipient = " + recipient);
    }
}
