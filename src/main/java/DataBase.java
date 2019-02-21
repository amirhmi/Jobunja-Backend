import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBase {
    private static DataBase me = null;
    public static User only_login_user = null;
    private List<User> users = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    public DataBase ()
    {
        if (me != null)
            return;
        me = this;
    }
    public static void setLoginUser(User user) {
        only_login_user = user;
        me.users.add(user);
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
            if (id.equals(user.getId()))
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
