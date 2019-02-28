import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import Model.Entity.DataBase;
import Model.Entity.User;
import com.sun.net.httpserver.HttpExchange;

public class UserPage implements IPage {

    @Override
    public void HandleRequest(HttpExchange http_exchange) throws IOException {
        String id = Server.findId(http_exchange);
        User user = DataBase.findUser(id);
        if (user == null)
            Server.sendForbidden(http_exchange);
        else {
            String response = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Model.Entity.User</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <ul>\n" +
                    "        <li>id: " + user.getId() + "</li>\n" +
                    "        <li>first name: " + user.getFirstName() + "</li>\n" +
                    "        <li>last name: " + user.getLastName() + "</li>\n" +
                    "        <li>jobTitle: " + user.getJobTitle() + "</li>\n" +
                    "        <li>bio: " + user.getBio() + "</li>\n" +
                    "    </ul>\n" +
                    "</body>\n" +
                    "</html>";
            byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
            http_exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = http_exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}
