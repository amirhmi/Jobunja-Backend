package Model.Service;

import DataAccess.*;
import Model.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class MiddlewareService {
    public static List<Project> getSuitedProjects(int page, int limit, boolean hasPage, String searchKey)
    {
        List<Project> projectList;
        if(!hasPage)
            projectList = ProjectDataMapper.getAll();
        else
            projectList = ProjectDataMapper.getLimit(limit, page*limit, getCurrentUserId(), searchKey);
        return projectList;
    }

    public static List<User> getUsersExceptCurrent(String searchKey) {
        return UserDataMapper.getAll(searchKey, true);
    }

    public static User getSpecificUser(int id)
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
        int currentUserId = getCurrentUserId();
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

    public static void endorseSkillForOtherUser (String skillName, int userId)
    {
        if (userId == getCurrentUserId())
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

    public static boolean userExists(int userId) { return UserDataMapper.exists(userId); }

    public static User getCurrentUser()  {
        return UserDataMapper.find(getCurrentUserId());
    }

    public static int getCurrentUserId()
    {
        return 1;
    }

    public static void addUser(User user) {
        UserDataMapper.insert(user);
    }

    public static void RemoveSkillForLoginUser(String skillName) {
        if (!UserSkillDataMapper.exists(skillName, getCurrentUserId()))
            throw new CustomException.SkillNotFoundForUserException();
        UserSkillDataMapper.delete(getCurrentUserId(), skillName);
    }

    public static List<String> CanBeAddedSkills() {
        return UserSkillDataMapper.findNotExistsForUser(getCurrentUserId());
    }

    public static int findByUsernamePassword(String userName, String password) {
        return UserDataMapper.findByUsernamePass(userName, password);
    }
}
