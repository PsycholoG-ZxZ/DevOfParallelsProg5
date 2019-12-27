import java.util.Optional;

public class UrlCountInfo {

    private Optional<String> link, count;

    public UrlCountInfo(Optional<String> link, Optional<String> count){
        this.count = count;
        this.link = link;
    }
    public Optional<String> getLink{
        return link;
    }
}
