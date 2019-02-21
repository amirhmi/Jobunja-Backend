import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Server {
    private static DataBase db = new DataBase();
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

    public static void sendResponse(HttpExchange http_exchange, int status, String response) throws IOException {
        http_exchange.sendResponseHeaders(status, response.length());
        OutputStream os = http_exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    class projectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange http_exchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(http_exchange.getRequestURI().getPath(), "/");
            tokenizer.nextToken();
            String page = tokenizer.nextToken();
            Class<IPage> page_class;
            switch (tokenizer.countTokens()){
                case 1 :
                    new ProjectPage().HandleRequest(http_exchange);
                    break;
                case 2:
                    new ProjectsPage().HandleRequest(http_exchange);
                    break;
                default:
                    String response =
                            "<html>"
                            + "<body>Page \""+ page + "\" not found.</body>"
                            + "</html>";
                    sendResponse(http_exchange, 400, response);
            }
        }
    }
}
