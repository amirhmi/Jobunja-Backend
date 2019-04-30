package Model.Service;

import DataAccess.*;
import Model.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class MiddlewareService {
    public static List<Project> getSuitedProjects(int page, int limit, boolean hasPage)
    {
        List<Project> projectList;
        if(!hasPage)
            projectList = ProjectDataMapper.getAll();
        else
            projectList = ProjectDataMapper.getLimit(limit, page*limit, getCurrentUserId());
        return projectList;
    }

    public static List<User> getUsersExceptCurrent() { return UserDataMapper.getAll(true); }

    public static User getSpecificUser(String id)
    {
        return UserDataMapper.find(id);
    }

    public static Project getSpecificProject(String id)
    {
        Project project = ProjectDataMapper.find(id);
        if(project != null && ProjectDataMapper.userSuited(id ,getCurrentUserId()))
            return project;
        return null;
    }

    public static Bid setBid(String id, int bidAmount) {
        if(!ProjectDataMapper.exists(id))
            throw new CustomException.ProjectNotFoundException();
        if(hasCurrentUserBidForProject(id))
            throw new CustomException.ProjectAlreadyBidExcption();
        String currentUserId = getCurrentUserId();
        Project project = ProjectDataMapper.find(id);
        if(!ProjectDataMapper.userSuited(id, getCurrentUserId()) || bidAmount > project.getBudget())
            throw new CustomException.NotSuitedProjectBidException();
        BidDataMapper.insert(id, currentUserId, bidAmount);
        return new Bid(getCurrentUserId(), bidAmount, id);
    }

    public static boolean hasCurrentUserBidForProject(String projectId) {
        return BidDataMapper.find(getCurrentUserId(), projectId) != null;
    }

    public static void addSkillForLoginUser(String skillName) throws CustomException.InvalidSkillNameException, CustomException.SkillAlreadyExistsException {
            if(currentUserHasSkill(skillName)) {
                throw new CustomException.SkillAlreadyExistsException();
            }
            UserSkillDataMapper.insert(getCurrentUserId(), skillName);
    }

    public static boolean currentUserHasSkill(String skillName)
    {
        return UserSkillDataMapper.exists(skillName, getCurrentUserId());
    }

    public static void endorseSkillForOtherUser (String skillName, String userId)
    {
        if (userId.equals(getCurrentUserId()))
            throw new CustomException.EndorseByOwnerException();
        try {
            EndorsementDataMapper.insert(getCurrentUserId(), userId, skillName);
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

    public static boolean userExists(String userId) { return UserDataMapper.exists(userId); }

    public static User getCurrentUser()  {
        return UserDataMapper.find(getCurrentUserId());
    }

    public static String getCurrentUserId()
    {
        return "1";
    }

    public static void RemoveSkillForLoginUser(String skillName) {
        if (!UserSkillDataMapper.exists(skillName, getCurrentUserId()))
            throw new CustomException.SkillNotFoundForUserException();
        UserSkillDataMapper.delete(getCurrentUserId(), skillName);
    }

    public static List<String> CanBeAddedSkills() {
        return UserSkillDataMapper.findNotExistsForUser(getCurrentUserId());
    }
}
