package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler  {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().toUpperCase().equals("GET")) {

            String urlPath = exchange.getRequestURI().toString();

            if (exchange.getRequestURI().toString().equals("/") || exchange.getRequestURI() == null) {
                urlPath = "/index.html";
            }

            String filePath = "web" + urlPath;
            File foundFile = new File(filePath);
            File notFoundFile = new File("web/HTML/404.html");

            if(foundFile.exists()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(foundFile.toPath(), respBody);
                exchange.close();
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(notFoundFile.toPath(), respBody);
                exchange.close();
            }

            exchange.getResponseBody().close();
        }
    }
}
