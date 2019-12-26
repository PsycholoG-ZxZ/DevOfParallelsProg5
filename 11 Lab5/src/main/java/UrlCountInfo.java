import java.util.Optional;

public class UrlCountInfo {

    private String link, count;

    public UrlCountInfo(Optional<String> link, String count){
        this.count = count;
        this.link = link;
    }
}
