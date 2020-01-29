import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static javax.swing.UIManager.get;

public class StorageActor extends AbstractActor {
    private Map<UrlCountInfo, Long> storage = new HashMap<>();

    /* Cоздаем в actorSystem — актор который принимает две команды — поиск
     * уже готового результата тестирования и результат тестрования.
     */

    @Override
    public Receive createReceive(){
        return ReceiveBuilder.create()
                .match(StoreMessage.class, f -> {
                    Long time = f.getTime();
                    storage.put(f.getTest(),time);
                })
                /*
                .match(UrlCountInfo.class, f -> {
                    Optional<String> link = f.getLink();
                    if (storage.containsKey(f)) {
                        sender().tell(new UrlCountInfo(link, f.getCount()), getSelf());
                    }
                })
                */
                .match(UrlCountInfo.class, f-> {
                    StoreMessage resMessage = new StoreMessage(storage.get(f), f);
                    sender().tell(new ResponseResult(resMessage),getSelf());
                })
                .build();
    }
}
