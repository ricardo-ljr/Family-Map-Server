package Handler;

import Request.RegisterRequest;
import Result.ResultBool;
import Services.RegisterService;
import com.google.gson.Gson;

public class RegisterHandler extends DefaultHandler{

    /**
     * Type of HTTP request
     */
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
