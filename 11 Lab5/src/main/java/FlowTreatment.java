import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Source;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class FlowTreatment {
    private Http http;
    private ActorSystem system;
    private ActorMaterializer materializer;
    ActorRef storeActor;

    public Flow<HttpRequest, HttpResponse, NotUsed> FlowTreatment(Http http, ActorSystem actorSys, ActorMaterializer mater){
        this.http = http;
        this.materializer = mater;
        this.system = actorSys;
        this.storeActor = system.actorOf(Props.create(StorageActor.class));

        return Flow.of(HttpRequest.class)
                .map(this::parserForTest)
                .mapAsync(4, f -> Patterns.ask(storeActor, f, Duration.ofMillis(5000))
                        .thenCompose(ms -> {
                            ResponseResult response = (ResponseResult) ms;
                            if (response.getFlag_about_contains() == 1){
                                return CompletableFuture.completedFuture(response);
                            }
                            return Source.from(Collections.singletonList(f))
                                    .toMat(testSink(), Keep.right()).run(materializer)
                                    .thenCompose(t -> CompletableFuture.completedFuture(new ResponseResult(0, )))

                        })
                )



    }

    public UrlCountInfo parserForTest(HttpRequest request){
        Optional<String> link = request.getUri().query().get("testUrl");
        Optional<String> count = request.getUri().query().get("count");

        UrlCountInfo TestInfo = new UrlCountInfo(link, count);
        return TestInfo;

    }

}
