import akka.actor.AbstractActor;

import java.util.HashMap;
import java.util.Map;

public class StorageActor {
    private Map<UrlCountInfo, String> storage = new HashMap<>();

    @Override
    public AbstractActor.Receive createReceive(){
        
    }
}
