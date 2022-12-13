package bank;

import bank.exceptions.*;

import java.util.*;

public class PrivateBankAlt implements Bank {

    private String name;

    private double incomingInterest;

    private double outgoingInterest;

    /**
     * Dieses Attribut soll Konten mit Transaktionen verknüpfen,
     * so dass jedem gespeicherten Konto 0 bis n Transaktionen zugeordnet werden können.
     */
    private Map<String, List<Transaction>> accountsToTransactions=new HashMap<String, List<Transaction>>();

    /**
     *
     * @param name Name der privaten Bank setzen
     */
    public void set_name (String name){this.name = name;}

    /**
     *
     * @return Name der privaten Bank
     */
    public String get_name (){return this.name;}
    /**
     *
     * @param in "die Zinse an, die bei einer Einzahlung anfallen" setzen
     */
    public void set_incomingInterest(double in){this.incomingInterest = in;}

    /**
     *
     * @return die Zinse an, die bei einer Einzahlung anfallen.
     */
    public double get_incomingInterest(){ return this.incomingInterest;}

    /**
     *
     * @param out" die Zinse an, die bei einer Auszahlung anfallen." setzen
     */
    public void set_outgoingInterest(double out){this.outgoingInterest = out;}

    /**
     *
     * @return  die Zinse an, die bei einer Auszahlung anfallen.
     */
    public double get_outgoingInterest(){ return this.outgoingInterest;}

    /**
     * Konstuktor
     * @param Name  Name der privaten Bank
     * @param IncomingInt die Zinse an, die bei einer Einzahlung anfallen.
     * @param OutgoingInt die Zinse an, die bei einer Auszahlung anfallen.
     */

    public PrivateBankAlt(String Name, double IncomingInt, double OutgoingInt ){
        this.name = Name;
        this.incomingInterest = IncomingInt;
        this.outgoingInterest = OutgoingInt;
    }

    /**
     * Copy-Konstruktor
     * @param P Objekt von Typ PrivateBank, das seine Attribute kopiert werden sollen.
     */
    public PrivateBankAlt(PrivateBankAlt P){
        this(P.name, P.incomingInterest, P.outgoingInterest);
    }
    /**
     *
     * @return den Inhalt aller Klassenattribute auf der Konsole ausgeben.
     */
    @Override
    public String toString(){
        return("Name: "+ this.name+
                " IncomingInterest: "+this.incomingInterest+
                " OutgoingInterest: "+ this.outgoingInterest);}

    /**
     *
     * @param other Objekt von Typ PrivateBank
     * @return true falls die beide Objekte gleich sind, sonst false
     */
    @Override
    public boolean equals(Object other) {
        //return(this == other);
        // Identität von this und other prüfen
        if (this == other) return true;

        // checking for two condition:
        // 1)prüfen, ob other eine null-Referenz ist.
        // 2)Test auf  Vergleichbarkeit

        if (this.getClass() != other.getClass()) return false;

        PrivateBankAlt P = (PrivateBankAlt) other; // type casting object to the

        // checking if the two
        // objects share all the same values
        return Objects.equals(P.get_name(), this.get_name()) &&
                Objects.equals(P.get_incomingInterest(), this.get_incomingInterest()) &&
                Objects.equals(P.get_outgoingInterest(), this.get_outgoingInterest()) &&
                Objects.equals(P.accountsToTransactions, this.accountsToTransactions);
    }

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {

        if(!(accountsToTransactions.containsKey(account))){
            List<Transaction> transaction= new ArrayList<Transaction>();
            accountsToTransactions.put(account, transaction);

        }
        else {
            throw new AccountAlreadyExistsException("Account already exists!");
        }

    }

    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {

        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException("Account already exists!");
        }
        else {
            accountsToTransactions.put(account, transactions);
        }
    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, AmountNotValidException {

        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account doesn't exist!");
        }
        else if(accountsToTransactions.containsValue(transaction))
        {
            throw new TransactionAlreadyExistException("Transaction already exists!");
        }
        else{
            if(transaction instanceof Payment){
                ((Payment) transaction).setIncomingInterest(this.incomingInterest);
                ((Payment) transaction).setOutgoingInterest(this.outgoingInterest);
            }
            accountsToTransactions.get(account).add(transaction);
        }
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {

        if(!accountsToTransactions.containsValue(transaction))
        {
            throw new TransactionDoesNotExistException("Transaction doesnt exist!");
        }
        else{
            accountsToTransactions.get(account).remove(transaction);
        }

    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {

        if (!accountsToTransactions.containsKey(account)) return false;
        else if(accountsToTransactions.containsValue(transaction)) return true;
        else return false;
    }

    @Override
    public double getAccountBalance(String account) {
        double balance = 0.00;
        if(accountsToTransactions.containsKey(account)){
            for(Transaction obj : accountsToTransactions.get(account)){
                if(obj instanceof Transfer){
                    Transfer trans = (Transfer)obj;
                    if(trans.getSender().equals(account))
                        balance -= trans.calculate();
                }
                balance += obj.calculate();
            }
        }
        return balance;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        List<Transaction> TransactionList = new ArrayList<>(accountsToTransactions.get(account));
        return TransactionList;
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        List<Transaction> TransactionList = new ArrayList<>(accountsToTransactions.get(account));

        if (asc) {

            TransactionList.sort((Comparator.comparing(Transaction::calculate)));


        } else {

            TransactionList.sort((Comparator.comparing(Transaction::calculate)).reversed());

        }
        return TransactionList;
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        return accountsToTransactions.get(account);
    }
}
