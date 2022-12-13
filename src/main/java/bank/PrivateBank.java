package bank;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import bank.exceptions.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class PrivateBank implements Bank {

    /**
     * name of this private bank
     */
    private String name;

    /**
     * incoming interest rate of this bank
     */
    private double incomingInterest;

    /**
     * outgoing interest rate of this bank
     */
    private double outgoingInterest;

    /**
     * name des Speicherorts
     */
    private String directoryName = "\\Bank_OOS_Gradle\\accounts/";


    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

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
     * Konstuktor: Creates a {@link PrivateBank}
     * @param Name  Name der privaten Bank
     * @param IncomingInt die Zinse an, die bei einer Einzahlung anfallen.
     * @param OutgoingInt die Zinse an, die bei einer Auszahlung anfallen.
     */
    public PrivateBank (String Name,double IncomingInt, double OutgoingInt, String directoryName) throws TransactionAlreadyExistException, AccountDoesNotExistException, AccountAlreadyExistsException, TransactionAttributeException, AmountNotValidException, IOException, ClassNotFoundException {
        this.name = Name;
        this.incomingInterest = IncomingInt;
        this.outgoingInterest = OutgoingInt;
        this.directoryName = directoryName;
        readAccounts();
    }

    /**
     * Copy-Konstruktor
     * @param P Objekt von Typ PrivateBank, das seine Attribute kopiert werden sollen.
     */
    public PrivateBank (PrivateBank P) throws TransactionAlreadyExistException, AccountDoesNotExistException, AccountAlreadyExistsException, TransactionAttributeException, AmountNotValidException, IOException, ClassNotFoundException {
        this(P.name, P.incomingInterest, P.outgoingInterest, P.directoryName);
        this.accountsToTransactions.putAll(P.accountsToTransactions);
        readAccounts();
    }
    /**
     *
     * @return den Inhalt aller Klassenattribute auf der Konsole ausgeben.
     */
    @Override
    public String toString(){
        return("Name: "+ this.name+
                "\nIncoming Interest: "+this.incomingInterest+
                "\nOutgoing Interest: "+ this.outgoingInterest);}

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

        PrivateBank P = (PrivateBank) other; // type casting object to the

        // checking if the two
        // objects share all the same values
        return Objects.equals(P.get_name(), this.get_name()) &&
                Objects.equals(P.get_incomingInterest(), this.get_incomingInterest()) &&
                Objects.equals(P.get_outgoingInterest(), this.get_outgoingInterest()) &&
                Objects.equals(P.accountsToTransactions, this.accountsToTransactions) &&
                Objects.equals(P.directoryName, this.directoryName)
                ;
    }

    /**
     *
     * @param account the account to be added
     * @throws AccountAlreadyExistsException
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {

        if(!(accountsToTransactions.containsKey(account))){
            List<Transaction> transaction= new ArrayList<Transaction>();
            accountsToTransactions.put(account, transaction);

        }
        else {
            throw new AccountAlreadyExistsException("Account already exists!");
        }
        writeAccount(account);
    }

    public void writeAccount(String account) throws IOException {
        //directoryName = "C:\\Users\\abdul\\IdeaProjects\\Bank_OOS_Gradle\\accounts/";

        File file = new File(directoryName + account +".json");

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream os = new BufferedOutputStream(fileOutputStream);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new CustomSerializer())
                .setPrettyPrinting()
                .create();

        //gsonBuilder.registerTypeAdapter(Payment.class, new CustomSerializer());
        //gsonBuilder.registerTypeAdapter(IncomingTransfer.class, new CustomSerializer());
        //gsonBuilder.registerTypeAdapter(OutgoingTransfer.class, new CustomSerializer());

        Type genericTypeOfList = new TypeToken<List<Transaction>>(){}.getType();

        String json = gson.toJson(accountsToTransactions.get(account), genericTypeOfList);

        os.write(json.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    /*public void readAccount(String account) throws IOException, AmountNotValidException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException {
        FileReader fileReader = new FileReader(account + ".json");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new CustomDeserializer());

        Gson gson = gsonBuilder.create();

        Type genericTypeOfList = new TypeToken<List<Transaction>>(){}.getType();

        List<Transaction> newTransactions = gson.fromJson(bufferedReader, genericTypeOfList);

        this.addTransaction(account, newTransactions.get(0));
    }*/

    public void readAccounts() throws IOException, AmountNotValidException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, ClassNotFoundException, AccountAlreadyExistsException {
        directoryName = "C:\\Users\\abdul\\IdeaProjects\\Bank_OOS_Gradle\\accounts/";

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Transaction.class, new CustomDeserializer())
                .setPrettyPrinting()
                .create();

        Type genericTypeOfList = new TypeToken<List<Transaction>>(){}.getType();

        File[] files = new File(directoryName).listFiles();

        Scanner sc;
        //Jede Datei einzeln einlesen

        for(File file: files) {
            //.json am Dateiende entfernen
            String accountName = file.getName().replaceFirst("[.][^.]+$", "");

            //Ganze Datei einlesen
            sc = new Scanner(file);
            StringBuffer bf = new StringBuffer();
            while(sc.hasNextLine()) {
                bf.append(sc.nextLine() + "\n");

            }


            List<Transaction> transactionList = gson.fromJson(bf.toString(), genericTypeOfList);

            if(!accountsToTransactions.containsKey(accountName))
                createAccount(accountName);

            for(Transaction trans : transactionList)
                addTransaction(accountName, trans);

            sc.close();
        }


        /*for(File file : files){
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedReader = new BufferedInputStream(fileInputStream);
            *//*FileReader fileInputStream = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileInputStream);*//*

            String filename = file.getName();
            String jsonpath = filename.substring(0, filename.indexOf(".json"));

            Stream<String> result = bufferedReader;

            JsonArray jsonArray = gson.fromJson((JsonElement) result, JsonArray.class);

            //List<JsonObject> parser = gson.fromJson(bufferedReader.toString(), genericTypeOfList);

            //List<Transaction> transactionList = (List<Transaction>) bufferedReader.readObject();


            accountsToTransactions.put(filename, gson.fromJson(jsonArray, genericTypeOfList));

            bufferedReader.close();
            fileInputStream.close();

            //JsonArray jsonArray = new JsonArray();
            //jsonArray =  gson.fromJson(result,JsonArray.class);

        }*/
    }


        @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, IOException {

        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException("Account already exists!");
        }
        else {
            accountsToTransactions.put(account, transactions);
        }
        writeAccount(account);
    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, AmountNotValidException, IOException {

        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account doesn't exist!");
        }
        else if(containsTransaction(account, transaction))
        {
            throw new TransactionAlreadyExistException("Transaction already exists!");
        }
        else{
            // Falls Transaction von Payment, werden incoming - und outgoinginterest ueberschrieben
            if(transaction instanceof Payment){
                ((Payment) transaction).setIncomingInterest(this.incomingInterest);
                ((Payment) transaction).setOutgoingInterest(this.outgoingInterest);
            }
            accountsToTransactions.get(account).add(transaction);
            writeAccount(account);
        }
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {

        if (!accountsToTransactions.containsKey(account)) {
            throw new AccountDoesNotExistException("Account doesn't exist!");
        }
        else
        if(!containsTransaction(account, transaction))
        {
            throw new TransactionDoesNotExistException("Transaction doesnt exist!");
        }
        else{
            accountsToTransactions.get(account).remove(transaction);
        }
        writeAccount(account);
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if(accountsToTransactions.containsKey(account)) {
            List<Transaction> tListTrans= accountsToTransactions.get(account);
            //Prüfe mit Equals statt mit List.contains()
            //Vergleiche Attribute nicht Objekte
            for(Transaction t : tListTrans) {
                if(t.getDate().equals(transaction.getDate()) && t.getDescription().equals(transaction.getDescription())
                        && t.getAmount() == transaction.getAmount())
                    return true;
            }
        }
        return false;

    }

    @Override
    public double getAccountBalance(String account) {
        double betrag = 0.00;
        if(accountsToTransactions.containsKey(account)){
            for(Transaction trans : accountsToTransactions.get(account)){
                betrag += trans.calculate();
            }
        }
        return betrag;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        //List<Transaction> TransactionList = new ArrayList<>(accountsToTransactions.get(account));
        return accountsToTransactions.get(account);
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

    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        Path path = FileSystems.getDefault().getPath(directoryName+account+".json");
        if(!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException(account);
        else {
            accountsToTransactions.remove(account);
            //File toDelete = new File(directoryName+account+".json");
            Files.delete(path);
        }
    }

    public List<String> getAllAccounts() {
        List<String> result = new ArrayList<String>();
        accountsToTransactions.forEach((key,value)->{
            result.add(key);
        });
        return result;
    }

}




