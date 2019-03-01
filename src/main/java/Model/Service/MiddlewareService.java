package Model.Service;

import Model.Entity.Bid;
import Model.Entity.DataBase;
import Model.Entity.Project;
import Model.Entity.User;

import java.util.ArrayList;
import java.util.List;

public class MiddlewareService {
    public static List<Project> getSuitedProjects(User user)
    {
        List<Project> projectList = DataBase.getProjects(), suitedProjects = new ArrayList<>();
        for (Project project : projectList)
            if (project.userSuited(user))
                suitedProjects.add(project);
        return suitedProjects;
    }

    public static List<User> getUsersExcept(User exceptUser)
    {
        List<User> userList = DataBase.getUsers(), ret = new ArrayList<>();
        for (User user : userList)
            if (user != exceptUser)
                ret.add(user);
        return userList;
    }

    public static Project getSpecificProject(String id)
    {
        List<Project> projectList = DataBase.getProjects();
        Project project = DataBase.findProject(id);
        if(project.userSuited(DataBase.only_login_user))
            return project;
        return null;
    }

    public static boolean setBid(String id, int bidAmount) {
        Project project = DataBase.findProject(id);
        if(project == null)
            return false;
        if(hasCurrentUserBidForProject(project))
            return false;
        Bid bid = new Bid(DataBase.only_login_user, bidAmount, project);
        boolean status = project.addBid(bid);
        return status;
    }

    public static boolean hasCurrentUserBidForProject(Project project) {
        for(Bid bid : project.getBids()) {
            if(bid.getUser().getId() == DataBase.only_login_user.getId())
                return true;
        }
        return false;
    }
}
