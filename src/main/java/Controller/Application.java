package Controller;

import DataAccess.*;
import Model.Entity.Project;
import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.HttpClientGet;
import Model.Service.JsonParser;
import Model.Service.MiddlewareService;
import Model.Service.RequestType;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Component
public class Application {
    private static HttpClientGet http_client_get = new HttpClientGet();
    @PostConstruct
    public void init() {
        try {
            String skills_data_json = http_client_get.HttpGetRequest(RequestType.SKILL);

            EntityInitializer.createTables();
            List<String> validSkillNames = JsonParser.parseNameList(skills_data_json);
            for(String skillName : validSkillNames)
                SkillDataMapper.insert(skillName);
            UserDataMapper.insert(new User("1", "علی", "شریف زاده", "برنامه نویس وب",
                    "https://i.ibb.co/wCkChfK/ali.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Javascript", 4), new Skill("C++", 2),
                    new Skill("Java", 3), new Skill("SQL", 3)));
            UserDataMapper.insert(new User("2", "امیرحسین", "احمدی", "دانش‌پژوه",
                    "https://i.ibb.co/5FRFzY8/amir.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Django", 6), new Skill("PHP", 2),
                    new Skill("Java", 3), new Skill("Node.js", 10)));
            UserDataMapper.insert(new User("3", "بهار", "باطنی", "گیمر",
                    "https://i.ibb.co/d5Y4PDK/bahar.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("C", 4), new Skill("C++", 7), new Skill("Photoshop", 1),
                    new Skill("Java", 4), new Skill("Linux", 3)));
            UserDataMapper.insert(new User("4", "محمد", "رضوی", "گرافیست",
                    "http://www.leseditionsducafe.com/wp-content/uploads/2016/06/male_cartoon_portrait_actual_by_ianmaiguapictures-d8pmfyo-768x681.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("C", 4), new Skill("C++", 7), new Skill("Photoshop", 1),
                    new Skill("Java", 4), new Skill("Linux", 3)));
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException ioException) {
            return;
        }
    }
}