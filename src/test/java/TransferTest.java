import bank.IncomingTransfer;
import bank.OutgoingTransfer;
import bank.Payment;
import bank.Transfer;
import bank.exceptions.AmountNotValidException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
public class TransferTest {

    //TODO Constructor, Copy-Constructor, calcule(), equals, toString

    Transfer transfer;

    @BeforeEach
    void clearUp() throws AmountNotValidException {
        assertDoesNotThrow(() -> {
            transfer = new Transfer("24.10.2022", 1000, "test transfer", "Atassi", "Max");
        });
    }

    @Test
    void testConstructor() throws AmountNotValidException {
        transfer = new Transfer("19.02.2022", 1000, "testing the constructor", "Atassi", "Max");
        assertNotNull(transfer);
        assertEquals(transfer.getDate(), "19.02.2022");
        assertEquals(transfer.getAmount(), 1000);
        assertEquals(transfer.getDescription(), "testing the constructor");
        assertEquals(transfer.getSender(), "Atassi");
        assertEquals(transfer.getRecipient(), "Max");
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1000})
    void shouldThrowAmountNotValidException(double args) throws AmountNotValidException {
        //payment = new Payment("24.10.2022", 3000, "test payment", args, args);
        Exception testException = assertThrows(AmountNotValidException.class, () -> {
            transfer.setAmount(args);
        });
        assertEquals("Amount not valid! Only positive values", testException.getMessage());
    }


    @Test
    void testCopyConstructor() throws AmountNotValidException {
        Transfer copyTransfer = new Transfer(transfer);
        assertEquals(transfer, copyTransfer);
    }

    @Test
    void testEquals() throws AmountNotValidException {
        Transfer copyTransfer = new Transfer("24.10.2022", 1000, "test transfer","Atassi", "Max");
        assertTrue(copyTransfer.equals(transfer));
    }


    @Test
    void testToString(){
        String expectedOutput = "Date: 24.10.2022\nDescription: test transfer\nAmount: 1000.0\nSender: Atassi\nRecipient: Max";
        assertEquals(transfer.toString(), expectedOutput);
    }

    @Test
    void testCalculate() throws AmountNotValidException {
        Transfer in = new IncomingTransfer(transfer);
        Transfer out = new OutgoingTransfer(transfer);

        transfer.setAmount(1000);
        double expected = 1000;
        assertEquals(in.calculate(), expected);

        expected = -1000;
        assertEquals(out.calculate(), expected);
    }
}
