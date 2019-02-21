import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBase {
    private static DataBase me = null;
    public static User only_login_user = new User("1", "علی", "شریف زاده", "برنامه نویس وب",
            "",
            "روی سنگ قبرم بنویسید: خدا بیامرز می خواست خیلی کارا بکنه ولی پول نداشت",
            new Skill("HTML", 5), new Skill("Javascript", 4), new Skill("c++", 2),
            new Skill("Java", 3));
    private List<User> users = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    public DataBase ()
    {
        if (me != null)
            return;
        me = this;
    }
    public static void addUser(User user)
    {
        me.users.add(user);
    }
    public static void addProject(Project project)
    {
        me.projects.add(project);
    }
    public static User findUser(String id)
    {
        for (User user : me.users)
            if (Objects.equals(user.getId(), id))
                return user;
        return null;
    }
    public static Project findProject(String id)
    {
        for (Project project : me.projects)
            if (Objects.equals(project.getId(), id))
                return project;
        return null;
    }

    public static List<Project> getProjects() { return me.projects; }
}
