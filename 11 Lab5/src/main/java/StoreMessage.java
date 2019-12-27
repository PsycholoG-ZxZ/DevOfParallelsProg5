import java.util.Optional;

public class StoreMessage {
    private long time;
    private UrlCountInfo test;

    public StoreMessage(long time, UrlCountInfo test){
        this.time = time;
        this.test = test;
    }
    public long getTime(){
        return time;
    }

    public UrlCountInfo getTest() {
        return test;
    }
}
