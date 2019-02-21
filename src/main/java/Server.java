import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.StringTokenizer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class Server {
    private static Server me;
    private static final String project_context = "/project";
    private static final String user_context = "/user";

    public void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext(project_context, new ProjectHandler());
        server.createContext(user_context, new UserHandler());
        server.setExecutor(null);
        server.start();
    }

    public static void sendResponse(HttpExchange http_exchange, int status, String response) throws IOException {
        http_exchange.sendResponseHeaders(status, response.length());
        OutputStream os = http_exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void sendBadRequest(HttpExchange http_exchange) throws IOException {
        String response =
                "<html>"
                        + "<body>Page not found.</body>"
                        + "</html>";
        sendResponse(http_exchange, 400, response);
    }

    class ProjectHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange http_exchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(http_exchange.getRequestURI().getPath(), "/");
            System.out.println("here " + tokenizer.countTokens());
            switch (tokenizer.countTokens()){
                case 1 :
                    new ProjectsPage().HandleRequest(http_exchange);
                    break;
                case 2:
                    new ProjectsPage().HandleRequest(http_exchange);
                    break;
                default:
                    sendBadRequest(http_exchange);
            }
        }
    }

    class UserHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange http_exchange) throws IOException {
            StringTokenizer tokenizer = new StringTokenizer(http_exchange.getRequestURI().getPath(), "/");
            tokenizer.nextToken();
            String page = tokenizer.nextToken();
            Class<IPage> page_class;
            switch (tokenizer.countTokens()){
                case 2:
                    new UserPage().HandleRequest(http_exchange);
                    break;
                default:
                    sendBadRequest(http_exchange);
            }
        }
    }

    public static String findId(HttpExchange http_exchange) {
        StringTokenizer tokenizer = new StringTokenizer(http_exchange.getRequestURI().getPath(), "/");
        tokenizer.nextToken();
        return tokenizer.nextToken();
    }
}
