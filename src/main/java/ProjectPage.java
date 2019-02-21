import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;

import javax.xml.crypto.Data;

public class ProjectPage implements IPage {

    @Override
    public void HandleRequest(HttpExchange http_exchange) throws IOException {
        Project project = DataBase.findProject(Server.findId(http_exchange));
        if (project == null)
        {
            Server.sendNotFound(http_exchange);
            return;
        }
        if (!project.userSuited(DataBase.only_login_user))
        {
            Server.sendForbidden(http_exchange);
            return;
        }
        String response = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Project</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>id: " + project.getId() + "</li>\n" +
                "        <li>title: " + project.getTitle() + "</li>\n" +
                "        <li>description: " + project.getDescription() + "</li>\n" +
                "        <li>imageUrl: <img src=\"" + project.getImageUrl() + "\" style=\"width: 50px; height: 50px;\"></li>\n" +
                "        <li>budget: " + project.getBudget() + "</li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>";
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        http_exchange.sendResponseHeaders(200, bytes.length);
        System.out.println(response);
        OutputStream os = http_exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

}
