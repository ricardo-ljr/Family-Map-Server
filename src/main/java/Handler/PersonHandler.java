package Handler;

import Result.ResultBool;
import Services.PersonByIdService;
import Services.PersonsService;

public class PersonHandler extends DefaultHandler{

    /**
     * Type of HTTP request
     */
    public PersonHandler() {
        getOrPost = "get";
        authenticate = true;
    }

    @Override
    protected ResultBool workWithService(String requestURI, String reqData) {
        System.out.println(reqData);

        String[] commands = requestURI.split("/");
        PersonsService service = new PersonsService();
        PersonByIdService serviceById = new PersonByIdService();
        if (commands.length>2)
            return serviceById.getPerson(commands[2], authToken);
        else
            return service.getPersons(authToken);
    }
}
