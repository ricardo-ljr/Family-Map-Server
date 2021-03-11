package Result;

/**
 * This class is responsible for sending messages to request that don't require unique implementations
 */
public class SuccessMessageResult extends ResultBool{

    private String message;

    /**
     * Load in a message for generalized result requests
     *
     */
    public SuccessMessageResult() {
        this.success = true;
    }

    public SuccessMessageResult(String message) {
        this.success = true;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
