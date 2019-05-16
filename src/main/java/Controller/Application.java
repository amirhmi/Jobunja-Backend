package Controller;

import DataAccess.*;
import Model.Entity.Skill;
import Model.Entity.User;
import Model.Service.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Application {
    private static HttpClientGet http_client_get = new HttpClientGet();
    @PostConstruct
    public void init() {
        try {
            EntityInitializer.createTables();
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new FetchProjects(), 0, 5, TimeUnit.MINUTES);
            scheduler.scheduleAtFixedRate(new UpdateWinners(), 0, 1, TimeUnit.MINUTES);
            String skills_data_json = http_client_get.HttpGetRequest(RequestType.SKILL);
            List<String> validSkillNames = JsonParser.parseNameList(skills_data_json);
            for(String skillName : validSkillNames)
                SkillDataMapper.insert(skillName);
            UserDataMapper.insert(new User("علی", "شریف زاده", "ali", Cryptography.getSHA("1"),"برنامه نویس وب",
                    "https://i.ibb.co/wCkChfK/ali.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Javascript", 4), new Skill("C++", 2),
                    new Skill("Java", 3), new Skill("SQL", 3)));
            UserDataMapper.insert(new User("امیرحسین", "احمدی", "amir", Cryptography.getSHA("2"), "دانش‌پژوه",
                    "https://i.ibb.co/5FRFzY8/amir.jpg",
                    "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
                    new Skill("HTML", 5), new Skill("Django", 6), new Skill("PHP", 2),
                    new Skill("Java", 3), new Skill("Node.js", 10)));
            UserDataMapper.insert(new User("بهار", "باطنی", "bahar", Cryptography.getSHA("3"), "گیمر",
                    "https://i.ibb.co/d5Y4PDK/bahar.jpg",
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