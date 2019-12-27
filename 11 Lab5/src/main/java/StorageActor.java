import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;

public class StorageActor {
    private Map<String, String> storage = new HashMap<>();

    @Override
    public AbstractActor.Receive createReceive(){
        return ReceiveBuilder.create()
                .match(StoreMessage.class, f -> storage.put(f.getTest()), );
    }
}
