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
    void init() throws AccountAlreadyExistsException, IOException, AmountNotValidException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, ClassNotFoundException {
        assertDoesNotThrow(() -> {
            musterbank = new PrivateBank("Muster Private Bank", 0.2, 0.5, null);
        });

        try {
            musterbank.createAccount("Atassi");
        } catch (Exception e){

        }
        payment = new Payment("11.05.2021", 1000, "deposit", 0.3, 0.1);
    }


    @AfterEach
    @Disabled
    void clearUp() throws AccountDoesNotExistException, IOException {
        for(String account : musterbank.getAllAccounts())
            musterbank.deleteAccount(account);
    }

    @Test
    void testConstructor(){
        assertEquals(musterbank.get_name(), "Muster Private Bank");
        assertEquals(musterbank.get_incomingInterest(), 0.2);
        assertEquals(musterbank.get_outgoingInterest(), 0.5);
    }

    @Test
    void testCopyConstructor() throws TransactionAlreadyExistException, AccountDoesNotExistException, AccountAlreadyExistsException, TransactionAttributeException, AmountNotValidException, IOException, ClassNotFoundException {
        PrivateBank copyBank = new PrivateBank(musterbank);
        assertEquals(copyBank, musterbank);
    }

    @Test
    void testToString(){
        String expectedOutput = "Name: Muster Private Bank\nIncoming Interest: 0.2\nOutgoing Interest: 0.5";
        assertEquals(musterbank.toString(), expectedOutput);
    }

    @Test
    void testEquals() throws TransactionAlreadyExistException, AccountDoesNotExistException, AccountAlreadyExistsException, TransactionAttributeException, AmountNotValidException, IOException, ClassNotFoundException {
        PrivateBank copyBank = new PrivateBank(musterbank);
        assertTrue(copyBank.equals(musterbank));
    }


    @Test
    void testCreateAccount() throws AccountAlreadyExistsException, IOException {
        assertNotNull(musterbank.getAccountBalance("Atassi"));
    }

    @Test
    void testAddTransaction() throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException, AmountNotValidException {
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
    void testRemoveTransaction() throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException, AmountNotValidException {
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
    void testAccountBalance(double args) throws AmountNotValidException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IOException {
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



}
