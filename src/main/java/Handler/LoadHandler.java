package Handler;

import Request.LoadRequest;
import Result.ResultBool;
import Services.LoadService;
import com.google.gson.Gson;

public class LoadHandler extends DefaultHandler{

    /**
     * Type of HTTP request
     */
    public LoadHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected ResultBool workWithService(String requestURI, String reqData) {
        System.out.println(reqData);

        Gson gson = new Gson();
        LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
        LoadService service = new LoadService();
        return service.load(request);
    }
}
