import java.util.Optional;

public class StoreMessage {
    private Long time;
    private UrlCountInfo test;

    public StoreMessage(Long time, UrlCountInfo test){
        this.time = time;
        this.test = test;
    }
    public Long getTime(){
        return time;
    }

    public UrlCountInfo getTest() {
        return test;
    }
}
