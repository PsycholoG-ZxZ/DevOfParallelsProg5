import java.util.Optional;

public class ResponseRes {
    private String link;
    private Long time;
    private int flag_about_contains; // 0 or 1

    public ResponseRes(int flag, String link, Long time){
        this.flag_about_contains = flag;
        this.link = link;
        this.time = time;
    }

    public int getFlag_about_contains() {
        return flag_about_contains;
    }

    public Long getTime() {
        return time;
    }

    public String getLink() {
        return link;
    }
}
