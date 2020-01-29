import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.ContentType;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import akka.util.ByteString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.AsyncHttpClient;
import sun.rmi.runtime.Log;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.regex.Pattern;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class FlowTreatment {
    private Http http;
    private ActorSystem system;
    private ActorMaterializer materializer;
    ActorRef storeActor;

    public FlowTreatment(Http http, ActorSystem system, ActorMaterializer materializer) {
        this.http = http;
        this.materializer = materializer;
        this.system = system;
        this.storeActor = system.actorOf(Props.create(StorageActor.class));
    }

    public Flow<HttpRequest, HttpResponse, NotUsed> FlowTreatmentM(Http http, ActorSystem actorSys, ActorMaterializer mater){
        this.http = http;
        this.materializer = mater;
        this.system = actorSys;
        this.storeActor = system.actorOf(Props.create(StorageActor.class));

        /* HttpRequest (наружний запрос) преобразуется в HttpResponse*/
        return Flow.of(HttpRequest.class)
                .map(this::parserForTest) //Создаем класс содержащий пару значения URL сайта и Количества запросов
                /*
                 * С помощью Patterns.ask посылаем запрос в кеширующий актор — есть ли результат
                 */
                .mapAsync(4, f -> Patterns.ask(storeActor, f, Duration.ofMillis(5000))
                        /* Обрабатываем ответ с помощью метода thenCompose */
                        .thenCompose(ms -> {
                            ResponseResult response = (ResponseResult) ms;
                            if (response.getResult().isPresent()){
                                return CompletableFuture.completedFuture(response); //Если результат уже высчитан, то возвращаем его как completedFuture
                            }
                            /* Если нет то тут же создаем флоу из данных запроса
                            *
                            * */
                            return Source.from(Collections.singletonList(f))
                                    .toMat(testSink(), Keep.right()).run(materializer)
                                    //.thenCompose(time -> CompletableFuture.completedFuture(new ResponseResult(0, f.getLink(),
                                    //        time / Long.parseLong(f.getCount().toString()))));
                                    .thenCompose(time -> CompletableFuture.completedFuture(new StoreMessage(time / Long.parseLong(f.getCount()), f)));
                        }))
                .map(resp -> {
                   // if (resp.getFlag_about_contains() != 1){
                   //     StoreMessage storeMessage = new StoreMessage(resp.getTime(), new UrlCountInfo(resp.getLink()
                   //             , resp.getTime().toString()));
                    storeActor.tell(resp, ActorRef.noSender());
                    String jsonString = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(resp);
                    return HttpResponse.create().withStatus(200).withEntity(ContentTypes.APPLICATION_JSON, ByteString.fromString(jsonString));
                });



    }

    public UrlCountInfo parserForTest(HttpRequest request){
        Optional<String> link = request.getUri().query().get("testUrl");
        Optional<String> count = request.getUri().query().get("count");

        UrlCountInfo  TestInfo = new UrlCountInfo(link.get(), count.get()); //Создаем класс содержащий пару значения URL сайта и Количества запросов
        return TestInfo;

    }

    static final Sink<UrlCountInfo, CompletionStage<Long>> testSink(){
        return Flow.<UrlCountInfo>create()
                .mapConcat(m -> Collections.nCopies(Integer.parseInt(m.getCount().toString()), m.getLink().toString()))
                .mapAsync(4, f ->{
                    Long Begin = System.currentTimeMillis();
                    AsyncHttpClient asyncHttpClient = asyncHttpClient();
                    return  asyncHttpClient.prepareGet(f).execute().toCompletableFuture().thenCompose(re ->
                        CompletableFuture.completedFuture(System.currentTimeMillis() - Begin)
                    );
                }).toMat(Sink.fold(0L,Long::sum), Keep.right());

    }

}
