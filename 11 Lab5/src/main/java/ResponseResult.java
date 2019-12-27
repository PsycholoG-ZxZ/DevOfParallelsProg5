import java.util.Optional;

public class ResponseResult {
    private Optional<String> link;
    private Long time;
    private int flag_about_contains; // 0 or 1

    public ResponseResult(int flag, Optional<String> link, Long time){
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

    public Optional<String> getLink() {
        return link;
    }
}
