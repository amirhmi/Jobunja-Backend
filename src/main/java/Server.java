import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

public class Server {
    private static DataBase db;
    private static final String project_context = "/project";
    private static final String user_context = "/user";

    public static void startServer(DataBase db) throws IOException {
        db = db;
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext(project_context);
        server.createContext(user_context);
        server.setExecutor(null);
        server.start();
    }

    class projectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange http_exchange) {
            String[] tokenized_path = http_exchange.getRequestURI().getPath().split("/");


        }
    }
}
