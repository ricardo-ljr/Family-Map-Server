package Handler;

import JSONReader.Deserializer;
import JSONReader.ReadWrite;
import JSONReader.Serializer;
import Request.LoadRequest;
import Result.LoadResult;
import Result.ResultBool;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if(exchange.getRequestMethod().toUpperCase().equals("POST")) {

                LoadService loadService = new LoadService();
                LoadResult response = new LoadResult();


                InputStream reqBody = exchange.getRequestBody();
                String reqData = ReadWrite.readString(reqBody);


                LoadRequest loadRequest = Deserializer.deserialize(reqData, LoadRequest.class);
                response = loadService.load(loadRequest);


                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String json = Serializer.serialize(response);
                OutputStream os = exchange.getResponseBody();
                ReadWrite.writeString(json, os);

            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            exchange.getResponseBody().close();
        } catch(IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
