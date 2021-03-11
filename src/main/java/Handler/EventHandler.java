package Handler;

import Services.EventsByIdService;
import Services.EventsResult;

public class EventHandler extends DefaultHandler{
    public EventHandler() {
        getOrPost = "get";
        authenticate = true;
    }
    @Override
    protected Response workWithService(String requestURI, String reqData) {
        System.out.println(reqData);

        String[] commands = requestURI.split("/");

        EventsByIdService serviceById = new EventsByIdService();
        EventsResult serviceAllEvents = new EventsResult();
        if (commands.length>2)
            return serviceById.getEventById(commands[2], authToken);
        else
            return serviceAllEvents.getAllEvents(authToken);
    }
}
