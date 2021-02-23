package Result;

/**
 * This class is responsible for sending messages to request that don't require unique implementations
 */
public class MessageResult {

    private String message;

    /**
     * Initializes empty constructor
     */
    public MessageResult (){}

    /**
     * Load in a message for generalized result requests
     *
     * @param message Message of success or error
     */
    public MessageResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
