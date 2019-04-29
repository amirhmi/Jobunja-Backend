package Model.Service;

import DataAccess.*;
import Model.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class MiddlewareService {
    public static List<Project> getSuitedProjects()
    {
        User currentUser = getCurrentUser();
        List<Project> projectList = ProjectDataMapper.getAll(), suitedProjects = new ArrayList<>();
        for (Project project : projectList)
            if (project.userSuited(currentUser))
                suitedProjects.add(project);
        return suitedProjects;
    }

    public static List<User> getUsersExceptCurrent()
    {
        List<User> userList = UserDataMapper.getAll(), ret = new ArrayList<>();
        User currentUser = getCurrentUser();
        for (User user : userList)
            if (!user.getId().equals(currentUser.getId()))
                ret.add(user);
        return ret;
    }

    public static User getSpecificUser(String id)
    {
        return UserDataMapper.find(id);
    }

    public static Project getSpecificProject(String id)
    {
        Project project = ProjectDataMapper.find(id);
        if(project != null && project.userSuited(getCurrentUser()))
            return project;
        return null;
    }

    public static Bid setBid(String id, int bidAmount) {
        Project project = ProjectDataMapper.find(id);
        if(project == null)
            throw new CustomException.ProjectNotFoundException();
        if(hasCurrentUserBidForProject(project))
            throw new CustomException.ProjectAlreadyBidExcption();
        String currentUserId = getCurrentUser().getId();
        Bid bid = new Bid(currentUserId, bidAmount, project.getId());
        try {
            project.addBid(bid);
            BidDataMapper.insert(project.getId(), currentUserId, bidAmount);
        }
        catch (Project.NotSuitedBidException e) {
            throw new CustomException.NotSuitedProjectBidException();
        }
        return bid;
    }

    public static boolean hasCurrentUserBidForProject(Project project) {
        for(Bid bid : project.getBids()) {
            if(bid.getUser().getId().equals(getCurrentUser().getId()))
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
                UserSkillDataMapper.insert(currentUser.getId(), skillName);
            }
            catch (SQLException e) {
                throw new CustomException.SqlException();
            }
    }

    public static Skill endorseSkillForOtherUser (String skillName, User user)
    {
        if (user == null)
            throw new CustomException.UserNotFoundException();
        User currentUser = getCurrentUser();
        if (user.getId().equals(currentUser.getId()))
            throw new CustomException.EndorseByOwnerException();
        try {
            Skill skill = user.endorseSkill(skillName, currentUser);
            EndorsementDataMapper.insert(currentUser.getId(), user.getId(), skillName);
            return skill;
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

    public static User getCurrentUser()  {
        return UserDataMapper.find("1");
    }

    public static void RemoveSkillForLoginUser(String skillName) {
        User currentUser = getCurrentUser();
        try {
            currentUser.removeSkill(skillName);
            UserSkillDataMapper.delete(currentUser.getId(), skillName);
        }
        catch (User.SkillNotFoundException e)
        {
            throw new CustomException.SkillNotFoundForUserException();
        }
    }

    public static List<String> CanBeAddedSkills() {
        try {
            User currentUser = getCurrentUser();
            List<String> skillNames = new ArrayList<>();
            for (String skillName : SkillDataMapper.getAll())
                if (!currentUser.hasSkill(skillName))
                    skillNames.add(skillName);
            return skillNames;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }

    }
}
