import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private static DataBase me = null;
    private List<Worker> workers = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();
    public DataBase ()
    {
        if (me != null)
            return;
        me = this;
    }
    public static void addWorker(Worker worker)
    {
        me.workers.add(worker);
    }
    public static void addProject(Project project)
    {
        me.projects.add(project);
    }
    public static Worker findWorker(String username)
    {
        for (Worker worker : me.workers)
            if (worker.getUsername() == username)
                return worker;
        return null;
    }
    public static Project findProject(String title)
    {
        for (Project project : me.projects)
            if (project.getTitle() == title)
                return project;
        return null;
    }
}
