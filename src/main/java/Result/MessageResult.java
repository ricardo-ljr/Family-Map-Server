package Result;

/**
 * This class is responsible for sending messages to request that don't require unique implementations
 */
public class MessageResult {

    private String Message;

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
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
