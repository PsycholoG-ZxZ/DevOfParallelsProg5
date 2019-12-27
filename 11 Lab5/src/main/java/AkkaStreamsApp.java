import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import jdk.internal.org.objectweb.asm.Handle;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class AkkaStreamsApp {
    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        ActorRef storeActor = system.actorOf(Props.create(StorageActor.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = new FlowTreatment(http, system, materializer);

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
                );
        System.out.println("server Online at localhost");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate()); // OFF when done
    }
    
}
