import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class FlowTreatment {
    private Http http;
    private ActorSystem system;
    private ActorMaterializer materializer;

    public Flow<HttpRequest, HttpResponse, NotUsed> FlowTreatment(Http http, ActorSystem actorSys, ActorMaterializer mater){
        this.http = http;
        this.materializer = mater;
        this.system = actorSys;

        return Flow.of(HttpRequest.class).map(this::parserForTest)



    }

    public parserForTest(HttpRequest request){
        String link = request.
    }

}
