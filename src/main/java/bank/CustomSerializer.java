package bank;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomSerializer implements JsonSerializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction src, Type typeOfSrc, JsonSerializationContext context) {

        JsonObject transaction = new JsonObject();
        JsonObject data = new JsonObject();

        transaction.addProperty("CLASSNAME", src.getClass().getSimpleName());
        transaction.add("INSTANCE", context.serialize(src));

        return transaction;
    }
}
