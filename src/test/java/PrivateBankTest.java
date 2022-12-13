import bank.*;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateBankTest {

    PrivateBank musterbank;

    Payment payment;
    Transfer transfer;


    /**
     * Init Methode für alle Unittests
     * ToDo: Aftereach dateien löschen
     */
    @BeforeEach
    @Test
    void init() throws AccountAlreadyExistsException, IOException, AmountNotValidException {
        assertDoesNotThrow(() -> {
            musterbank = new PrivateBank("Muster Private Bank", 0.2, 0.5, "null");
        });
        musterbank.createAccount("Atassi");
        payment = new Payment("11.05.2021", 1000, "deposit", 0.3, 0.1);
    }

    @Test
    void testConstructor(){
        assertEquals(musterbank.get_name(), "Muster Private Bank");
        assertEquals(musterbank.get_incomingInterest(), 0.2);
        assertEquals(musterbank.get_outgoingInterest(), 0.5);
    }

    @Test
    void testCopyConstructor(){
        PrivateBank copyBank = new PrivateBank(musterbank);
        assertEquals(copyBank, musterbank);
    }

    @Test
    void testToString(){
        String expectedOutput = "Name: Muster Private Bank\nIncoming Interest: 0.2\nOutgoing Interest: 0.5";
        assertEquals(musterbank.toString(), expectedOutput);
    }

    @Test
    void testEquals(){
        PrivateBank copyBank = new PrivateBank(musterbank);
        assertTrue(copyBank.equals(musterbank));
    }


    @Test
    void testCreateAccount() throws AccountAlreadyExistsException, IOException {
        assertNotNull(musterbank.getAccountBalance("Atassi"));
    }

    @Test
    void testAddTransaction() throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, FileNotFoundException, AmountNotValidException {
        musterbank.addTransaction("Atassi", payment);

        assertThrows(AccountDoesNotExistException.class, () -> {
            musterbank.addTransaction("non existent account", payment);
        });
        assertThrows(TransactionAlreadyExistException.class, ()-> {
            musterbank.addTransaction("Atassi", payment);
        });


        assertTrue(musterbank.containsTransaction("Atassi", payment));
    }

    @Test
    void testRemoveTransaction() throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, FileNotFoundException, AmountNotValidException {
        assertThrows(AccountDoesNotExistException.class, () -> {
            musterbank.removeTransaction("non existent account", payment);
        });
        assertThrows(TransactionDoesNotExistException.class, ()-> {
            musterbank.removeTransaction("Atassi", payment);
        });

        musterbank.addTransaction("Atassi", payment);

        assertDoesNotThrow(()-> {
            musterbank.removeTransaction("Atassi", payment);
        });

        assertFalse(musterbank.containsTransaction("Atassi", payment));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1000 })
    void testAccountBalance(double args) throws AmountNotValidException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, FileNotFoundException {
        musterbank.addTransaction("Atassi", new IncomingTransfer("15.05.2001", args, "Test income transfer"));

        assertEquals(musterbank.getAccountBalance("Atassi"), args);

        musterbank.addTransaction("Atassi", new OutgoingTransfer("15.05.2001", args, "Test outgoing transfer"));

        assertEquals(musterbank.getAccountBalance("Atassi"), 0);
    }


    @Test
    void testGetTransactions() throws TransactionDoesNotExistException, IOException, AmountNotValidException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        Transaction transaction1 = new Payment("03.01.1998", 3000, "partial deposit");
        Transaction transaction2 = new Payment("10.03.2008", 4500, "partial deposit 2");
        Transaction transaction3 = new IncomingTransfer("15.05.2001", 250, "Test income transfer");

        musterbank.addTransaction("Atassi",  transaction1);
        musterbank.addTransaction("Atassi", transaction2);
        musterbank.addTransaction("Atassi",  transaction3);

        List<Transaction> transactionList = musterbank.getTransactions("Atassi");

        assertTrue(transactionList.contains(transaction1));
        assertTrue(transactionList.contains(transaction2));
        assertTrue(transactionList.contains(transaction3));
    }

    @Test
    void testGetTransactionsSorted() {
        List<Transaction> tListAsc = pB.getTransactionsSorted("Test", true);
        if(tListAsc.size() == 0)
            return;
        //Prüfe ob nachfolgendes Element in Liste immer größer
        double prev = tListAsc.get(0).calculate();
        for(Transaction t: tListAsc) {
            assertTrue(t.calculate()>=prev);
            prev = t.getAmount();
        }

        //Descending
        List<Transaction> tListDsc = pB.getTransactionsSorted("Test", false);
        if(tListAsc.size() == 0)
            return;
        //Prüfe ob nachfolgendes Element in Liste immer kleiner
        prev = tListAsc.get(0).calculate();
        for(Transaction t: tListAsc) {
            assertTrue(t.calculate()<=prev);
            prev = t.getAmount();
        }

    }



    /*
    @Test
    void testPrivateBankPrivateBank() throws IOException {
        PrivateBank pNeu = new PrivateBank(pB);
        assertEquals(pNeu,pB);
    }


    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#addTransaction(java.lang.String, bank.Praktikum.Transaction)}.
     *//*
    @ParameterizedTest
    @ValueSource(doubles= {100, -100, 1000})
    void testAddTransaction(double amount) {
        //Exceptions prüfen
        Payment p = new Payment("Heute", amount, "Payment: " + amount);
        assertThrows(AccountDoesNotExistException.class,()->{
            pB.addTransaction("Not Existing", new Payment("",100,""));
        });
        assertThrows(TransactionAlreadyExistException.class,()->{

            pB.addTransaction("Test", p);
            pB.addTransaction("Test", p);
        });
        assertTrue(pB.containsTransaction("Test", p));
    }

    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#removeTransaction(java.lang.String, bank.Praktikum.Transaction)}.
     *//*
    @Test
    void testRemoveTransaction() {
        Payment p = new Payment("Heute", 100, "Existiert nicht");
        assertThrows(TransactionDoesNotExistException.class, ()->{
            pB.removeTransaction("Test", p);
        });
        //Add Transaction to remove it later

        try {
            pB.addTransaction("Test", p);
        } catch (TransactionAlreadyExistException | AccountDoesNotExistException | IOException e) {
        }

        assertDoesNotThrow(()->{
            pB.removeTransaction("Test", p);
        });
        assertFalse(pB.containsTransaction("Test", p));

    }

    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#containsTransaction(java.lang.String, bank.Praktikum.Transaction)}.
     * @throws IOException
     * @throws AccountDoesNotExistException
     * @throws TransactionAlreadyExistException
     *//*
    @Test
    void testContainsTransaction() throws TransactionAlreadyExistException, AccountDoesNotExistException, IOException {
        assertTrue(pB.containsTransaction("Philipp", pTest));
    }

    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#getAccountBalance(java.lang.String)}.
     * @throws AccountDoesNotExistException
     *//*
    @Test
    void testGetAccountBalance() throws AccountDoesNotExistException {
        double balance = pB.getAccountBalance("Test");

        assertEquals(830, balance);
        assertThrows(AccountDoesNotExistException.class, ()->{
            pB.getAccountBalance("Not existing");
        });
    }

    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#getTransactions(java.lang.String)}.
     * @throws IOException
     * @throws TransactionDoesNotExistException
     *//*
    @Test
    void testGetTransactions() throws TransactionDoesNotExistException, IOException {

        List<Transaction> tList = pB.getTransactions("Test");
        //Check for previously added transactions
        assertTrue(pB.containsTransaction("Test",new Payment("Heute", -100, "Payment: -100.0")));
        assertTrue(pB.containsTransaction("Test",new Payment("Heute", 100, "Payment: 100.0")));
        assertTrue(pB.containsTransaction("Test",new Payment("Heute", 1000, "Payment: 1000.0")));
    }

    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#getTransactionsSorted(java.lang.String, boolean)}.
     *//*
    @Test
    void testGetTransactionsSorted() {
        List<Transaction> tListAsc = pB.getTransactionsSorted("Test", true);
        if(tListAsc.size() == 0)
            return;
        //Prüfe ob nachfolgendes Element in Liste immer größer
        double prev = tListAsc.get(0).calculate();
        for(Transaction t: tListAsc) {
            assertTrue(t.calculate()>=prev);
            prev = t.getAmount();
        }

        //Descending
        List<Transaction> tListDsc = pB.getTransactionsSorted("Test", false);
        if(tListAsc.size() == 0)
            return;
        //Prüfe ob nachfolgendes Element in Liste immer kleiner
        prev = tListAsc.get(0).calculate();
        for(Transaction t: tListAsc) {
            assertTrue(t.calculate()<=prev);
            prev = t.getAmount();
        }

    }

    *//**
     * Test method for {@link bank.Praktikum.PrivateBank#getTransactionsByType(java.lang.String, boolean)}.
     *//*
    @Test
    void testGetTransactionsByType() {
        List<Transaction> tList = pB.getTransactionsByType("Test", true);
        for(Transaction t: tList)
            assertTrue(t.calculate()>=0);
        tList = pB.getTransactionsByType("Test", false);
        for(Transaction t: tList)
            assertTrue(t.calculate()<=0);

    }*/


}
