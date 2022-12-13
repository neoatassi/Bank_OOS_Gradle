import bank.*;
import bank.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {

    public static void createAccounts(PrivateBank musterbank) throws Exception
    {
        // Declaration of a new Payment objects and initialization through "all attributes" constructor
        Payment complete_deposit = new Payment("11.05.2021", 1000, "deposit", 0.3, 0.1);
        Payment complete_withdrawal = new Payment("23.06.2021", -1000, "withdrawal", 0.05, 0.1);

        // Declaration of new Payment objects and initialization through "three attributes" constructor
        Payment partial_deposit = new Payment("03.01.1998", 3000, "partial deposit");
        Payment partial_withdrawal = new Payment("10.03.2008", -3000, "partial withdrawal");

        // Declaration of new Payment objects and initialization through copy constructor
        Payment complete_deposit_copy = new Payment(complete_deposit);
        Payment partial_withdrawal_copy = new Payment(partial_withdrawal);

        // Declaration of a new Transfer objects and initialization through "all attributes" constructor
        Transfer transfer1 = new IncomingTransfer("15.05.2001", 500, "First transfer", "Max", "Atassi");
        Transfer transfer2 = new IncomingTransfer("31.04.2021", 300, "Second transfer", "Andrew", "Atassi");
        Transfer transferback = new OutgoingTransfer("31.04.2021", 200, "Second transfer", "Atassi", "Abu Bakr");

        // Declaration of new Transfer objects and initialization through "three attributes" constructor
        Transfer transfer3 = new Transfer("21.09.2005", 405, "Third transfer");
        Transfer transfer4 = new Transfer("17.02.1995", 300, "Fourth transfer");

        // Declaration of new Transfer objects and initialization through copy constructor
        Transfer transfer1_copy = new Transfer(transfer1);
        Transfer transfer3_copy = new Transfer(transfer3);

        /*
        System.out.println("Calculated amounts: ");
        System.out.println(complete_deposit.calculate());
        System.out.println(complete_withdrawal.calculate());
        System.out.println(transfer1.calculate());
        System.out.println(transfer4.calculate());
        System.out.println("Testing toString() ");
        System.out.println(partial_withdrawal.toString());
        System.out.println(partial_withdrawal_copy.toString());
        System.out.println(transfer1.toString());
        System.out.println(transfer1_copy.toString());
        */

        //PrivateBank musterbank = new PrivateBank("MusterBank", 0.3, 0.1, "blabla");
        PrivateBank musterbank2 = new PrivateBank("Muster2", 0.2, 0.5, "blabla");
        PrivateBankAlt altmusterbank = new PrivateBankAlt("AltMusterBank", 0.3, 0.1);

        PrivateBank copyMusterbank = new PrivateBank(musterbank);


        try {
            musterbank.createAccount("Atassi");
            musterbank.addTransaction("Atassi", transfer1);
            musterbank.addTransaction("Atassi", transfer1);
            musterbank.addTransaction("Atassi", transfer2);
            musterbank.addTransaction("Atassi", transferback);
            musterbank.addTransaction("Atassi", partial_deposit);
            // System.out.println("Account balance of Atassi of Musterbank is " + musterbank.getAccountBalance("Atassi"));

            System.out.println(musterbank.getAccountBalance("Atassi"));

            musterbank.writeAccount("Atassi");

            musterbank.createAccount("Max");
            musterbank.addTransaction("Max", transfer3);
            musterbank.addTransaction("Max", transfer4);
            musterbank.addTransaction("Max", transferback);
            musterbank.addTransaction("Max", complete_deposit);

            musterbank.writeAccount("Max");

            //System.out.println(musterbank.getTransactions("Atassi"));
            //System.out.println(musterbank.getTransactionsSorted("Atassi", true));
            //System.out.println(musterbank.getTransactionsByType("Atassi", true));


            /*
            altmusterbank.createAccount("Alternative");
            altmusterbank.addTransaction("Alternative", complete_deposit);
            altmusterbank.addTransaction("Alternative", partial_deposit);
            altmusterbank.addTransaction("Alternative", transfer2);
            */
            //System.out.println("Account balance of Alternative of AltMusterbank is " + altmusterbank.getAccountBalance("Alternative"));

            // Testen von neuen Exception
            //Payment faultyPayment = new Payment("11.05.2021", 1000, "deposit", 3, 2);
            //Transfer faultyTransfer = new Transfer("31.04.2021", -1000, "Second transfer", "Andrew", "Joe");


            /*copyMusterbank.createAccount("Atassi");
            copyMusterbank.addTransaction("Atassi", complete_deposit);
            copyMusterbank.addTransaction("Atassi", partial_deposit);
            copyMusterbank.addTransaction("Atassi", transfer2);*/



            //System.out.println(musterbank.equals(copyMusterbank));

        /*
        } catch (TransactionAlreadyExistException | AccountDoesNotExistException | AccountAlreadyExistsException | AmountNotValidException | TransactionAttributeException e) {
            e.printStackTrace();
        }
        */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readAccounts(PrivateBank musterbank) throws Exception{
        try {

            musterbank.readAccounts();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        PrivateBank musterbank = new PrivateBank("MusterBank", 0.3, 0.1, "blabla");

        //musterbank.getTransactions("Atassi");

        System.out.println("HELLOOOOO");


        createAccounts(musterbank);


        //readAccounts(musterbank);
        System.out.println(musterbank.getTransactions("Atassi"));
        //System.out.println(musterbank.getTransactions("Muster"));



        /*
        catch (AccountDoesNotExistException e) {
            e.printStackTrace();
        } catch (AccountAlreadyExistsException e) {
            e.printStackTrace();
            //} catch (FileNotFoundException e){
            //e.printStackTrace();
            //} catch (IOException e){
            //  e.printStackTrace();
            // } catch (ClassNotFoundException e){
            //  e.printStackTrace();
        } catch(AmountNotValidException e){
            e.printStackTrace();
        } catch (TransactionAttributeException e) {
            e.printStackTrace();
        }
        */


    }
}
