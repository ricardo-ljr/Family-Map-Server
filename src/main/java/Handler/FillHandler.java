package Handler;

import Result.ResultBool;
import Services.FillService;
import com.google.gson.Gson;

public class FillHandler extends DefaultHandler {

    /**
     * Type of HTTP request
     */
    public FillHandler() {
        getOrPost = "post";
        authenticate = false;
    }

    @Override
    protected ResultBool workWithService(String requestURI, String reqData) {

        System.out.println(reqData);

        String[] commands = requestURI.split("/");
        String username = commands[2];

        Gson gson = new Gson();
        FillService service = new FillService();

        if (commands.length>3)
            return service.fill(username, Integer.parseInt(commands[3]));
        else
            return service.fill(username, 4);
    }
}
