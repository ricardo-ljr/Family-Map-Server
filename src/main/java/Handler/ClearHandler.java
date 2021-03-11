package Handler;

import Result.ResultBool;
import Services.ClearService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ClearHandler extends DefaultHandler {

    /**
     * Type of HTTP request
     */
    public ClearHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected ResultBool workWithService (String requestURI, String reqData) {
        ClearService service = new ClearService();
        return service.clearResult();
    }
}

