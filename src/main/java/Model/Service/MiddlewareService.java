package Model.Service;

import Model.Entity.*;

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

    public static User getSpecificUser(String id)
    {
        return DataBase.findUser(id);
    }

    public static Project getSpecificProject(String id)
    {
        Project project = DataBase.findProject(id);
        if(project != null && project.userSuited(DataBase.only_login_user))
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

    public static boolean addSkillForLoginUser(String skillName) {
        try {
            User currentUser = getCurrentUser();
            if(currentUser.hasSkill(skillName)) {
                return false;
            }
            Skill skill = new Skill(skillName);
            currentUser.addSkill(skill);
            return true;

        }
        catch (Skill.InvalidSkillNameException e) {
            return false;
        }
    }

    public static boolean endorseSkillForOtherUser (String skillName, User user)
    {
        if (user == null || user.getId().equals(getCurrentUser().getId()))
            return false;
        return user.endorseSkill(skillName, user);
    }

    public static User getCurrentUser() {
        return DataBase.only_login_user;
    }

    public static boolean RemoveSkillForLoginUser(String skillName) {
        User currentUser = getCurrentUser();
        return currentUser.removeSkill(skillName);
    }

    public static List<String> CanBeAddedSkills() {
        User currentUser = getCurrentUser();
        List<String> skillNames = new ArrayList<>();
        for (String skillName : Skill.getValidNames())
            if (!currentUser.hasSkill(skillName))
                skillNames.add(skillName);
        return skillNames;
    }
}
