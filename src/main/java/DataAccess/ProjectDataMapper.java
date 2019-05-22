package DataAccess;

import Model.Entity.Bid;
import Model.Entity.Project;
import Model.Entity.Skill;
import Exception.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDataMapper {

    public static Project find(String projectId)  {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM Project WHERE id = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, projectId);
            ResultSet rs = dbStatement.executeQuery();
            rs.next();
            rs.getString("Id");
            Project project = fillProject(rs);
            rs.close();
            dbStatement.close();
            db.close();
            return project;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException.SqlException();
        }
    }

    public static boolean userSuited(String projectId, int userId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT " +
                    "NOT EXISTS (SELECT * FROM ProjectSkill PS " +
                    "WHERE PS.projectId = ? AND " +
                    "PS.point > (SELECT COUNT(*) FROM Endorsement E WHERE E.skillName = PS.skillName AND E.endorsedId = ?))";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, projectId);
            dbStatement.setInt(2, userId);
            ResultSet rs = dbStatement.executeQuery();
            rs.next();
            boolean ret = rs.getBoolean(1);
            rs.close();
            dbStatement.close();
            db.close();
            return ret;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static boolean exists(String projectId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT EXISTS (SELECT * FROM Project WHERE id = ?)";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, projectId);
            ResultSet rs = dbStatement.executeQuery();
            rs.next();
            boolean ret = rs.getBoolean(1);
            rs.close();
            dbStatement.close();
            db.close();
            return ret;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static List<Project> getAll() {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM Project";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            ResultSet rs = dbStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (rs.next())
                projects.add(fillProject(rs));
            rs.close();
            dbStatement.close();
            db.close();
            return projects;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static List<Project> getLimit(int limit, int offset, int userId, String searchKey) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM Project P " +
                               "WHERE NOT EXISTS (SELECT * FROM ProjectSkill PS " +
                               "WHERE PS.projectId = P.id AND " +
                               "PS.point > (SELECT COUNT(*) FROM Endorsement E " +
                               "WHERE E.skillName = PS.skillName AND E.endorsedId = ?)) ";
            if (searchKey != null)
                statement += "AND (P.title LIKE ? OR P.description LIKE ?)";
            statement += "ORDER By creationDate DESC LIMIT ? OFFSET ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
            if (searchKey != null) {
                dbStatement.setString(2, "%" + searchKey + "%");
                dbStatement.setString(3, "%" + searchKey + "%");
                dbStatement.setInt(4, limit);
                dbStatement.setInt(5, offset);
            }
            else
            {
                dbStatement.setInt(2, limit);
                dbStatement.setInt(3, offset);
            }
            ResultSet rs = dbStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (rs.next())
                projects.add(fillProject(rs));
            rs.close();
            dbStatement.close();
            db.close();
            return projects;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException.SqlException();
        }
    }

    public static List<Project> getRecentlyEnded() {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM Project P " +
                    "WHERE P.deadline < ? AND ? - P.deadline <= 121000"; //less than one minute passed
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long nowTime = timestamp.getTime();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            System.out.println("now is " + nowTime);
            dbStatement.setLong(1, nowTime);
            dbStatement.setLong(2, nowTime);
            ResultSet rs = dbStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (rs.next())
                projects.add(fillProject(rs));
            rs.close();
            dbStatement.close();
            db.close();
            return projects;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    private static Project fillProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getString("Id"));
        project.setTitle(rs.getString("title"));
        project.setDescription(rs.getString("description"));
        project.setImageUrl(rs.getString("imageUrl"));
        project.setBudget(rs.getInt("budget"));
        project.setDeadline(rs.getLong("deadline"));
        project.setCreationDate(rs.getLong("creationDate"));
        int winnerId = rs.getInt("winnerId");
        if(winnerId != 0)
            project.setWinner(UserDataMapper.find(winnerId));
        int ownerId = rs.getInt("ownerId");
        if(ownerId != 0)
            project.setOwner(UserDataMapper.find(ownerId));
        List<Skill> skills = ProjectSkillDataMapper.findByProject(rs.getString("id"));
        project.setSkills(skills);
        List<Bid> bids = BidDataMapper.findByProject(project.getId());
        project.setBids(bids);
        return project;
    }

    public static void insert(Project project) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement =
                "INSERT IGNORE INTO Project(id, title, description, imageUrl, budget, deadline, creationDate)" +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, project.getId());
        dbStatement.setString(2, project.getTitle());
        dbStatement.setString(3, project.getDescription());
        dbStatement.setString(4, project.getImageUrl());
        dbStatement.setInt(5, project.getBudget());
        dbStatement.setLong(6, project.getDeadline());
        dbStatement.setLong(7, project.getCreationDate());
        dbStatement.execute();
        dbStatement.close();
        dbStatement.close();
        db.close();
    }

    public static void setWinner (String projectId, int winnerId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "UPDATE Project SET winnerId = ? WHERE id = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, winnerId);
            dbStatement.setString(2, projectId);
            dbStatement.executeUpdate();
            dbStatement.close();
            db.close();
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }
}
