import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.util.Optional;
import java.util.regex.Pattern;

public class FlowTreatment {
    private Http http;
    private ActorSystem system;
    private ActorMaterializer materializer;
    ActorRef storeActor = system.

    public Flow<HttpRequest, HttpResponse, NotUsed> FlowTreatment(Http http, ActorSystem actorSys, ActorMaterializer mater){
        this.http = http;
        this.materializer = mater;
        this.system = actorSys;

        return Flow.of(HttpRequest.class)
                .map(this::parserForTest)
                .mapAsync(4, f -> Patterns.ask()
                )



    }

    public UrlCountInfo parserForTest(HttpRequest request){
        Optional<String> link = request.getUri().query().get("testUrl");
        Optional<String> count = request.getUri().query().get("count");

        UrlCountInfo TestInfo = new UrlCountInfo(link, count);
        return TestInfo;

    }

}
