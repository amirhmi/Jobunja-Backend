package Model.Service;

import DataAccess.*;
import Model.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class MiddlewareService {
    public static List<Project> getSuitedProjects(int page, int limit, boolean hasPage, String searchKey, int currentUserId)
    {
        List<Project> projectList;
        if(!hasPage)
            projectList = ProjectDataMapper.getAll();
        else
            projectList = ProjectDataMapper.getLimit(limit, page*limit, currentUserId, searchKey);
        return projectList;
    }

    public static List<User> getUsersExceptCurrent(String searchKey, int userId) {
        return UserDataMapper.getAll(searchKey, true, userId);
    }

    public static User getSpecificUser(int id)
    {
        return UserDataMapper.find(id);
    }

    public static Project getSpecificProject(String id, int currentUserId)
    {
        Project project = ProjectDataMapper.find(id);
        if(project != null && ProjectDataMapper.userSuited(id ,currentUserId))
            return project;
        return null;
    }

    public static Bid setBid(String id, int bidAmount, int currentUserId) {
        if(!ProjectDataMapper.exists(id))
            throw new CustomException.ProjectNotFoundException();
        if(hasCurrentUserBidForProject(id, currentUserId))
            throw new CustomException.ProjectAlreadyBidExcption();
        Project project = ProjectDataMapper.find(id);
        if(!ProjectDataMapper.userSuited(id, currentUserId) || bidAmount > project.getBudget())
            throw new CustomException.NotSuitedProjectBidException();
        BidDataMapper.insert(id, currentUserId, bidAmount);
        return new Bid(currentUserId, bidAmount, id);
    }

    public static boolean hasCurrentUserBidForProject(String projectId, int currentUserId) {
        return BidDataMapper.find(currentUserId, projectId) != null;
    }

    public static void addSkillForLoginUser(String skillName, int currentUserId) throws CustomException.InvalidSkillNameException, CustomException.SkillAlreadyExistsException {
            if(currentUserHasSkill(skillName, currentUserId)) {
                throw new CustomException.SkillAlreadyExistsException();
            }
            UserSkillDataMapper.insert(currentUserId, skillName);
    }

    public static boolean currentUserHasSkill(String skillName, int currentUserId)
    {
        return UserSkillDataMapper.exists(skillName, currentUserId);
    }

    public static void endorseSkillForOtherUser (String skillName, int userId, int currentUserId)
    {
        if (userId == currentUserId)
            throw new CustomException.EndorseByOwnerException();
        try {
            EndorsementDataMapper.insert(currentUserId, userId, skillName);
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

    public static User getCurrentUser(int currentUserId)  {
        return UserDataMapper.find(currentUserId);
    }

    public static void addUser(User user) {
        UserDataMapper.insert(user);
    }

    public static void RemoveSkillForLoginUser(String skillName, int currentUserId) {
        if (!UserSkillDataMapper.exists(skillName, currentUserId))
            throw new CustomException.SkillNotFoundForUserException();
        UserSkillDataMapper.delete(currentUserId, skillName);
    }

    public static List<String> CanBeAddedSkills(int currentUserId) {
        return UserSkillDataMapper.findNotExistsForUser(currentUserId);
    }

    public static List<Project> getRecentlyEndedProjects() {
        return ProjectDataMapper.getRecentlyEnded();
    }

    public static int findByUsernamePassword(String userName, String password) {
        return UserDataMapper.findByUsernamePass(userName, password);
    }}
