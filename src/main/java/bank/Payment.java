package bank;

import bank.exceptions.AmountNotValidException;

public class Payment extends Transaction {
    /**
     * Einzahlungszinsen
     */
    private double incomingInterest = 0.0;

    /**
     * Auszahlungszinsen
     */
    private double outgoingInterest = 0.0;

    public Payment(String date, double amount, String description) throws AmountNotValidException {
        super(date, amount, description);
    }

    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) throws AmountNotValidException {
        this(date, amount, description);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    /**
     * Copy constructor
     * @param payment
     */
    public Payment(Payment payment) throws AmountNotValidException {
        this(payment.getDate(),
                payment.getAmount(),
                payment.getDescription(),
                payment.getIncomingInterest(),
                payment.getOutgoingInterest()
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

    public void setAmount(double amount) throws AmountNotValidException {
        super.setAmount(amount);
    }

    public String getDescription() {
        return super.getDescription();
    }

    public void setDescription(String description) {
        super.setDescription(description);
    }


    public double getIncomingInterest() {
        return incomingInterest;
    }

    public void setIncomingInterest(double incomingInterest) throws AmountNotValidException{
        if (incomingInterest >= 0.0 && incomingInterest <= 1.0) this.incomingInterest = incomingInterest;
        else throw new AmountNotValidException("Invalid input! Only values between 0 and 1 allowed");
    }

    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterest) {
        if(outgoingInterest <= 1.0 && outgoingInterest >= 0.0) this.outgoingInterest = outgoingInterest;
        else System.out.println("Invalid input! Only values between 0 and 1 allowed");
    }

    @Override
    public double calculate(){
        if(getAmount() >= 0) return getAmount() - (getAmount() * getIncomingInterest());
        else return getAmount() - ( -1 * getAmount() * getOutgoingInterest()) ;
    }

    @Override
    public String toString(){
        return super.toString() +
                "\nIncoming Interest: " + getIncomingInterest() +
                "\nOutgoing Interest: "  + getOutgoingInterest();
    }

    @Override
    public boolean equals(Object obj){

        if(!super.equals(obj)) return false;

        Payment temp = (Payment) obj;

        return temp.getAmount() == this.getAmount() &&
                temp.getIncomingInterest() == this.getIncomingInterest() &&
                temp.getOutgoingInterest() == this.getOutgoingInterest();

    }

    /**
     * gibt werte alle Klassenattribute aus
     */
    public void printObject() {
        System.out.println("date = " + date);
        System.out.println("amount = " + amount);
        System.out.println("description = " + description);
        System.out.println("incomingInterest = " + incomingInterest);
        System.out.println("outgoingInterest = " + outgoingInterest);
    }
}
