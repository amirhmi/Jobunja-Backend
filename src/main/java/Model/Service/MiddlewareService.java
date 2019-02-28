package Model.Service;

import Model.Entity.DataBase;
import Model.Entity.Project;
import Model.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class MiddlewareService {
    public static List<Project> getSuitedProjects(User user)
    {
        List<Project> projectList = DataBase.getProjects(), ret = new ArrayList<>();
        for (Project project : projectList)
            if (project.userSuited(user))
                ret.add(project);
        return ret;
    }
}
