import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

public class ProjectsPage implements IPage {

    @Override
    public void HandleRequest(HttpExchange http_exchange) throws IOException {
        String response = createHtmlResForProjects();
        http_exchange.sendResponseHeaders(200, response.length());
        OutputStream os = http_exchange.getResponseBody();
        System.out.println(response.getBytes());
        os.write(response.getBytes());
        System.out.println(response);
        os.close();
    }

    public String createHtmlResForProjects() {
        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Projects</title>\n" +
                "    <style>\n" +
                "        table {\n" +
                "            text-align: center;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "        td {\n" +
                "            min-width: 300px;\n" +
                "            margin: 5px 5px 5px 5px;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table>\n" +
                "        <tr>\n" +
                "            <th>id</th>\n" +
                "            <th>title</th>\n" +
                "            <th>budget</th>\n" +
                "        </tr>\n";
        for (Project p : DataBase.getProjects()) {
            html +=
                    "<tr>\n" +
                    "<td>" + p.getId() + "</td>\n" +
                    "<td>" + "title" + "</td>\n" +
                    "<td>" + Integer.toString(p.getBudget()) + "</td>\n" +
                    "</tr>\n";
        }
        html += "</table>\n" +
                "</body>\n" +
                "</html>";

        return html;
    }
}
