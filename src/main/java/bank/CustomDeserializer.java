package bank;

import bank.exceptions.AmountNotValidException;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CustomDeserializer implements JsonDeserializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject data = json.getAsJsonObject();

        String transactionType = data.get("CLASSNAME").getAsString();
        JsonObject instance = data.get("INSTANCE").getAsJsonObject();

        Transaction transaction = null;
        
        if(transactionType.equals("Payment"))
            transaction = context.deserialize(instance, Payment.class);
         else if (transactionType.equals("IncomingTransfer"))
            transaction = context.deserialize(instance, IncomingTransfer.class);
         else if (transactionType.equals("OutgoingTransfer"))
             transaction = context.deserialize(instance, OutgoingTransfer.class);


        return transaction;
    }
}
