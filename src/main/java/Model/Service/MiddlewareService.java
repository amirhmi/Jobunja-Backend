package Model.Service;

import DataAccess.ProjectDataMapper;
import DataAccess.SkillDataMapper;
import DataAccess.UserDataMapper;
import Model.Entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class MiddlewareService {
//    public static List<Project> getSuitedProjects()
//    {
//        User currentUser = getCurrentUser();
//        List<Project> projectList = ProjectDataMapper.getProjects(), suitedProjects = new ArrayList<>();
//        for (Project project : projectList)
//            if (project.userSuited(currentUser))
//                suitedProjects.add(project);
//        return suitedProjects;
//    }

//    public static List<User> getUsersExceptCurrent()
//    {
//        List<User> userList = DataBase.getUsers(), ret = new ArrayList<>();
//        User CurrentUser = getCurrentUser();
//        for (User user : userList)
//            if (!user.getId().equals(CurrentUser.getId()))
//                ret.add(user);
//        return userList;
//    }
//
//    public static User getSpecificUser(String id)
//    {
//        return DataBase.findUser(id);
//    }
//
//    public static Project getSpecificProject(String id)
//    {
//        Project project = DataBase.findProject(id);
//        if(project != null && project.userSuited(DataBase.only_login_user))
//            return project;
//        return null;
//    }
//
//    public static Bid setBid(String id, int bidAmount) {
//        Project project = DataBase.findProject(id);
//        if(project == null)
//            throw new CustomException.ProjectNotFoundException();
//        if(hasCurrentUserBidForProject(project))
//            throw new CustomException.ProjectAlreadyBidExcption();
//        Bid bid = new Bid(MiddlewareService.getCurrentUser(), bidAmount, project);
//        try {
//            project.addBid(bid);
//        }
//        catch (Project.NotSuitedBidException e) {
//            throw new CustomException.NotSuitedProjectBidException();
//        }
//        return bid;
//    }
//
//    public static boolean hasCurrentUserBidForProject(Project project) {
//        for(Bid bid : project.getBids()) {
//            if(bid.getUser().getId() == DataBase.only_login_user.getId())
//                return true;
//        }
//        return false;
//    }
//
//    public static void addSkillForLoginUser(String skillName) throws CustomException.InvalidSkillNameException, CustomException.SkillAlreadyExistsException {
//            User currentUser = getCurrentUser();
//            if(currentUser.hasSkill(skillName)) {
//                throw new CustomException.SkillAlreadyExistsException();
//            }
//            try {
//                Skill skill = new Skill(skillName);
//                currentUser.addSkill(skill);
//            }
//            catch (SQLException e) {
//                throw new CustomException.SqlException();
//            }
//            catch (Skill.InvalidSkillNameException e)
//            {
//                throw new CustomException.InvalidSkillNameException();
//            }
//    }
//
//    public static Skill endorseSkillForOtherUser (String skillName, User user)
//    {
//        if (user == null)
//            throw new CustomException.UserNotFoundException();
//        if (user.getId().equals(getCurrentUser().getId()))
//            throw new CustomException.EndorseByOwnerException();
//        try {
//            return user.endorseSkill(skillName, getCurrentUser());
//        }
//        catch (User.SkillNotFoundException e)
//        {
//            throw new CustomException.SkillNotFoundForUserException();
//        }
//        catch (Skill.AlreadyEndorsedException e)
//        {
//            throw new CustomException.SkillAlreadyEndorsedException();
//        }
//    }
//
    public static User getCurrentUser()  {
        try {
            return UserDataMapper.find("1");
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }
//
//    public static void RemoveSkillForLoginUser(String skillName) {
//        User currentUser = getCurrentUser();
//        try {
//            currentUser.removeSkill(skillName);
//        }
//        catch (User.SkillNotFoundException e)
//        {
//            throw new CustomException.SkillNotFoundForUserException();
//        }
//    }
//
//    public static List<String> CanBeAddedSkills() {
//        try {
//            User currentUser = getCurrentUser();
//            List<String> skillNames = new ArrayList<>();
//            for (String skillName : SkillDataMapper.getAll())
//                if (!currentUser.hasSkill(skillName))
//                    skillNames.add(skillName);
//            return skillNames;
//        }
//        catch (SQLException e) {
//            throw new CustomException.SqlException();
//        }
//
//    }
}
