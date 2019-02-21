import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;

public interface IPage {
    public void HandleRequest(HttpExchange http_exchange) throws IOException;
}