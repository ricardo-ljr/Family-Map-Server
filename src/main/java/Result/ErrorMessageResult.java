package Result;

public class ErrorMessageResult extends ResultBool{
    String message;

    /**
     * Error message
     *
     * @param message
     */
    public ErrorMessageResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
