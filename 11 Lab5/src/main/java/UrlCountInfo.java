import java.util.Optional;

public class UrlCountInfo {

    private String link, count;
    private String cnt;

    public UrlCountInfo(String link, String count){
        this.count = count;
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    public String getCount() {
        return count;
    }
}
