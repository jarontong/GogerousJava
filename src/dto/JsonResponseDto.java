package dto;

public class JsonResponseDto<T> {

    private int statueCode;
    private String message;
    private T  results;

    public JsonResponseDto(){}

    public JsonResponseDto(int statueCode, String message, T results) {
        this.statueCode = statueCode;
        this.message = message;
        this.results = results;
    }

    public int getStatueCode() {
        return statueCode;
    }

    public void setStatueCode(int statueCode) {
        this.statueCode = statueCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "JsonResponseBean{" +
                "statueCode=" + statueCode +
                ", message='" + message + '\'' +
                ", results=" + results +
                '}';
    }
}
