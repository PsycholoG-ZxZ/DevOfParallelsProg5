import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StorageActor {
    private Map<UrlCountInfo, String> storage = new HashMap<>();

    @Override
    public AbstractActor.Receive createReceive(){
        return ReceiveBuilder.create()
                .match(StoreMessage.class, f -> {
                    Long time = f.getTime();
                    storage.put(f.getTest(),time.toString());
                })
                .match(UrlCountInfo.class, f -> {
                    Optional<String> link = f.getLink();
                    sender().tell(new UrlCountInfo(link))
                }
    }
}
