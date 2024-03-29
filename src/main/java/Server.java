import Handler.ClearHandler;
import com.sun.net.httpserver.HttpServer;

import Handler.*;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    private HttpServer server;

    /**
     * Initialize empty constructor for Server
     */
    public Server () {}

    private void run(String portNumber) {
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    12);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new FileHandler());

        server.start();

        System.out.println("Server running on port 8000!");
    }

    /**
     * Main function to run server!
     *
     * @param args
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }

}

