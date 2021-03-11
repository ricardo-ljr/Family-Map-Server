package Handler;

import Request.LoginRequest;
import Result.ResultBool;
import Services.LoginService;
import com.google.gson.Gson;

public class LoginHandler extends DefaultHandler {

    /**
     * Type of HTTP request
     */
    public LoginHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected ResultBool workWithService(String requestURI, String reqData) {
        System.out.println(reqData);

        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(reqData, LoginRequest.class);
        LoginService service = new LoginService();
        return service.login(request);
    }
}
