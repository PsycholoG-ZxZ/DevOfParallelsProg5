import java.util.Optional;

public class UrlCountInfo {

    private Optional<String> link, count;
    private String cnt;

    public UrlCountInfo(Optional<String> link, Optional<String> count){
        this.count = count;
        this.link = link;
    }
    public UrlCountInfo(Optional<String> link, String count){
        this.cnt = count;
        this.link = link;
    }
    public Optional<String> getLink(){
        return link;
    }

    public Optional<String> getCount() {
        return count;
    }
}
