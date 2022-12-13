import bank.Payment;
import bank.PrivateBank;
import bank.exceptions.AccountAlreadyExistsException;
import bank.exceptions.AmountNotValidException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class PaymentTest {

    // TODO testConstructor, testCopyConstructor, testCalculate, testEquals, testToString
    Payment payment;

    @BeforeEach
    void clearUp() throws AmountNotValidException {
        payment = new Payment("24.10.2022", 1000, "test payment");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, 1.0})
    void testConstructor(double args) throws AmountNotValidException {
        payment = new Payment("19.02.2022", 1000, "testing the constructor", args, args);
        assertNotNull(payment);
        assertEquals(payment.getDate(), "19.02.2022");
        assertEquals(payment.getAmount(), 1000);
        assertEquals(payment.getDescription(), "testing the constructor");
        assertEquals(payment.getIncomingInterest(), args);
        assertEquals(payment.getOutgoingInterest(), args);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.3, 1.2})
    void shouldThrowAmountNotValidException(double args) throws AmountNotValidException {
        //payment = new Payment("24.10.2022", 3000, "test payment", args, args);
        Exception testException = assertThrows(AmountNotValidException.class, () -> {
           payment.setIncomingInterest(args);
        });
        assertEquals("Invalid input! Only values between 0 and 1 allowed", testException.getMessage());
    }

    @Test
    void testCopyConstructor() throws AmountNotValidException {
        Payment copyPayment = new Payment(payment);
        assertEquals(payment, copyPayment);
    }

    @Test
    void testEquals() throws AmountNotValidException {
        Payment copyPayment = new Payment("24.10.2022", 1000, "test payment");
        assertTrue(copyPayment.equals(payment));
    }

    @Test
    void testToString(){
        String expectedOutput = "Date: 24.10.2022\nDescription: test payment\nAmount: 1000.0\nIncoming Interest: 0.0\nOutgoing Interest: 0.0";
        assertEquals(payment.toString(), expectedOutput);
    }

    @Test
    void testCalculate() throws AmountNotValidException {
        payment.setIncomingInterest(0.01);
        payment.setOutgoingInterest(0.1);

        payment.setAmount(1000);
        double expected = 990.0;
        assertEquals(payment.calculate(), expected);

        payment.setAmount(-1000);
        expected = -1100.0;
        assertEquals(payment.calculate(), expected);
    }


}
