import java.util.Optional;

public class ResponseResult {
    private StoreMessage result;

    public ResponseResult(StoreMessage res){
        this.result = res;
    }

    public Optional<Object> getResult() {
        if (result.getTime() == null){
            return Optional.empty();
        }else{
            return Optional.of(result);
        }
    }
}
