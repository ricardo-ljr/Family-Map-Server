package Result;

public class ResultBool {
    boolean success;
    private String message;

    /**
     * Returns true or false
     *
     * @return
     */
    public boolean isSuccess() {

        return success;
    }

    /**
     * Set success to true or false
     *
     * @param success
     */
    public void setSuccess(boolean success) {

        this.success = success;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String s) {

        this.message = message;
    }


}
