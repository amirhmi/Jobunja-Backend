package Model.Service;

import Model.Entity.DataBase;
import Model.Entity.Project;
import Model.Entity.Skill;
import Model.Entity.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.List;

@WebListener
public class InitializeListner implements ServletContextListener {
    private static DataBase db = new DataBase();
    private static HttpClientGet http_client_get = new HttpClientGet();

    @Override
    public final void contextInitialized(final ServletContextEvent sce) {
        try {
            String project_data_json = http_client_get.HttpGetRequest(RequestType.PROJECT);
            String skills_data_json = http_client_get.HttpGetRequest(RequestType.SKILL);
            Skill.setValidNames(JsonParser.parseNameList(skills_data_json));
            List<Project> projs = JsonParser.parseProjectList(project_data_json);
            for(Project p : projs)
                DataBase.addProject(p);
            DataBase.setLoginUser(new User("1", "علی", "شریف زاده", "برنامه نویس وب",
                    "https://i.ibb.co/wCkChfK/ali.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Javascript", 4), new Skill("C++", 2),
                    new Skill("Java", 3)));
            DataBase.addUser(new User("2", "امیرحسین", "احمدی", "دانش‌پژوه",
                    "https://i.ibb.co/fM9grbc/amir.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Django", 6), new Skill("PHP", 2),
                    new Skill("Java", 3), new Skill("Node.js", 10)));
            DataBase.addUser(new User("3", "بهار", "باطنی", "گیمر",
                    "https://i.ibb.co/d5Y4PDK/bahar.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("C", 4), new Skill("C++", 7), new Skill("Photoshop", 1),
                    new Skill("Java", 4), new Skill("Linux", 3)));
        }
        catch (IOException ioException) {
            return;
        }
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent sce) {

    }
}