import javafx.util.Pair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static DataBase db = new DataBase();
    private static Server server = new Server();
    private static HttpClientGet http_client_get = new HttpClientGet();

    public static void main(String[] args) {
        try {
            String project_data_json = http_client_get.HttpGetRequest(RequestType.PROJECT);
            String skills_data_json = http_client_get.HttpGetRequest(RequestType.SKILL);
            List<Project> projs = JsonParser.parseProjectList(project_data_json);
            for(Project p : projs)
                DataBase.addProject(p);
            Skill.setValidNames(JsonParser.parseNameList(skills_data_json));
            DataBase.setLoginUser(new User("1", "علی", "شریف زاده", "برنامه نویس وب",
                    "",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Javascript", 4), new Skill("c++", 2),
                    new Skill("Java", 3)));
            server.startServer();
        }
        catch (IOException ioException) {
            return;
        }

    }
}
