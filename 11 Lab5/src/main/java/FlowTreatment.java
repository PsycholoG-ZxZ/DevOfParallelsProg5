import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

public class FlowTreatment {

    public Flow<HttpRequest, HttpResponse, NotUsed> FlowTreatment(Http, ActorSystem, ActorMaterializer){


    }

}
