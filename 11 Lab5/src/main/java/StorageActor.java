import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StorageActor extends  {
    private Map<UrlCountInfo, String> storage = new HashMap<>();

    @Override
    public Receive createReceive(){
        return ReceiveBuilder.create()
                .match(StoreMessage.class, f -> {
                    Long time = f.getTime();
                    storage.put(f.getTest(),time.toString());
                })
                .match(UrlCountInfo.class, f -> {
                    Optional<String> link = f.getLink();
                    if (storage.containsKey(f)) {
                        sender().tell(new UrlCountInfo(link, f.getCount()), getSelf());
                    }
                }).build();
    }
}
