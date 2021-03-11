import Handler.ClearHandler;
import com.sun.net.httpserver.HttpServer;

import Handler.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) {
        int port = 8000;
        Server myServer = new Server();
        try {
            myServer.run(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void run(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
        System.out.println("Server listening on port " + port);
    }

    private void registerHandlers(HttpServer server) {
        server.createContext("/clear", new ClearHandler());
//      server.createContext("/user/register", new RegisterHandler());
//      server.createContext("/", new FileHandler());
//      server.createContext("/user/login", new LoginHandler());
//      server.createContext("/fill", new FillHandler());
//      server.createContext("/load", new LoadHandler());
//      server.createContext("/person", new PersonHandler());
//      server.createContext("/event", new EventHandler());
    }
}

