import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;

public class StorageActor {
    private Map<UrlCountInfo, String> storage = new HashMap<>();

    @Override
    public AbstractActor.Receive createReceive(){
        return ReceiveBuilder.create()
                .match(UrlCountInfo.class, f -> storage.put(f.))
    }
}
