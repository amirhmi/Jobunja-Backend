package Model.Service;

import Model.Entity.*;

import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class MiddlewareService {
    public static List<Project> getSuitedProjects()
    {
        User currentUser = getCurrentUser();
        List<Project> projectList = DataBase.getProjects(), suitedProjects = new ArrayList<>();
        for (Project project : projectList)
            if (project.userSuited(currentUser))
                suitedProjects.add(project);
        return suitedProjects;
    }

    public static List<User> getUsersExceptCurrent()
    {
        List<User> userList = DataBase.getUsers(), ret = new ArrayList<>();
        User CurrentUser = getCurrentUser();
        for (User user : userList)
            if (!user.getId().equals(CurrentUser.getId()))
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

    public static void addSkillForLoginUser(String skillName) throws CustomException.InvalidSkillNameException, CustomException.SkillAlreadyExistsException {
            User currentUser = getCurrentUser();
            if(currentUser.hasSkill(skillName)) {
                throw new CustomException.SkillAlreadyExistsException();
            }
            try {
                Skill skill = new Skill(skillName);
                currentUser.addSkill(skill);
            }
            catch (Skill.InvalidSkillNameException e)
            {
                throw new CustomException.InvalidSkillNameException();
            }
    }

    public static void endorseSkillForOtherUser (String skillName, User user)
    {
        if (user == null)
            throw new CustomException.UserNotFoundException();
        if (user.getId().equals(getCurrentUser().getId()))
            throw new CustomException.EndorseByOwnerException();
        try {
            user.endorseSkill(skillName, user);
        }
        catch (User.SkillNotFoundException e)
        {
            throw new CustomException.SkillNotFoundForUserException();
        }
        catch (Skill.AlreadyEndorsedException e)
        {
            throw new CustomException.SkillAlreadyEndorsedException();
        }
    }

    public static User getCurrentUser() {
        return DataBase.only_login_user;
    }

    public static void RemoveSkillForLoginUser(String skillName) {
        User currentUser = getCurrentUser();
        try {
            currentUser.removeSkill(skillName);
        }
        catch (User.SkillNotFoundException e)
        {
            throw new CustomException.SkillNotFoundForUserException();
        }
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
