package bank;

import bank.exceptions.AmountNotValidException;

import java.util.Objects;

public abstract class Transaction implements CalculateBill{
    /**
     * Zeitpunkt einer Ein- oder Auszahlung bzw. einer Ueberweisung
     */
    protected String date;
    /**
     * Geldmenge einer Ein- oder Auszahlung bzw. einer Ueberweisung
     */
    protected double amount;
    /**
     * Beschreibung des Vorgangs
     */
    protected String description;

    public Transaction(String date, double amount, String description) throws AmountNotValidException {
        this.date = date;
        setAmount(amount);
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) throws AmountNotValidException {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return "Date: " + getDate() +  "\nDescription: " + getDescription() +
        "\nAmount: " + this.calculate() ;
    }

    @Override
    public boolean equals(Object obj){

        if(obj == this) return true;

        if(!(obj instanceof Transaction)) return false;

        Transaction temp = (Transaction) obj;

        return Objects.equals(temp.getDate(), this.getDate()) &&
                Objects.equals(temp.getDescription(), this.getDescription()) &&
                temp.getAmount() == this.getAmount();
    }

}
