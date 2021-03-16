package Handler;

import DAO.DataAccessException;
import JSONReader.ReadWrite;
import JSONReader.Serializer;
import Result.PersonByIdResult;
import Result.PersonsResult;
import Services.PersonByIdService;
import Services.PersonsService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if(exchange.getRequestMethod().toUpperCase().equals("GET")) {
                if(exchange.getRequestHeaders().containsKey("Authorization")) {

                    PersonByIdResult responseID = new PersonByIdResult();
                    PersonsResult responseAll = new PersonsResult();

                    PersonByIdService personIDService = new PersonByIdService();
                    PersonsService personAllService = new PersonsService();

                    String authID = exchange.getRequestHeaders().getFirst("Authorization");

                    String uri = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(uri);
                    url.deleteCharAt(0);
                    String[] paths = url.toString().split("/");

                    if(paths.length == 1) {

                        responseAll = personAllService.getPersons(authID);

                        if(responseAll.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        String json = Serializer.serialize(responseAll);
                        OutputStream os = exchange.getResponseBody();
                        ReadWrite.writeString(json, os);

                    } else if(paths.length == 2){

                        String personID = paths[1];
                        responseID = personIDService.getPerson(personID, authID);

                        if(responseID.isSuccess()) {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        } else {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        }

                        String json = Serializer.serialize(responseID);
                        OutputStream os = exchange.getResponseBody();
                        ReadWrite.writeString(json, os);

                    } else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                }
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            exchange.getResponseBody().close();
        } catch(IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
