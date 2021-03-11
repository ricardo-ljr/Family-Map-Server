package Handler;

import Request.RegisterRequest;
import Result.RegisterResultSuccess;
import Result.ResultBool;
import Services.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterHandler extends DefaultHandler{

    public RegisterHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected ResultBool workWithService(String requestURI, String reqData) {
        System.out.println(reqData);

        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
        RegisterService service = new RegisterService();

        return service.register(request);
    }

}
