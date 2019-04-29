package DataAccess;

import Model.Entity.Bid;
import Model.Entity.Project;
import Model.Entity.Skill;
import Exception.CustomException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDataMapper {

    public static Project find(String projectId)  {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM project WHERE id = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, projectId);
            ResultSet rs = dbStatement.executeQuery();
            Project project = fillProject(rs);
            rs.close();
            dbStatement.close();
            db.close();
            return project;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static List<Project> getAll() {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM project";
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

    private static Project fillProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getString("Id"));
        project.setTitle(rs.getString("title"));
        project.setDescription(rs.getString("description"));
        project.setImageUrl(rs.getString("imageUrl"));
        project.setBudget(rs.getInt("budget"));
        project.setDeadline(rs.getLong("deadline"));
        project.setCreationDate(rs.getLong("creationDate"));
        String winnerId = rs.getString("winnerId");
        if(winnerId != null)
            project.setWinner(UserDataMapper.find(winnerId));
        String ownerId = rs.getString("ownerId");
        if(ownerId != null)
            project.setOwner(UserDataMapper.find(ownerId));
        //TODO: get and set bids
        List<Skill> skills = ProjectSkillDataMapper.findByProject(rs.getString("id"));
        project.setSkills(skills);
        List<Bid> bids = BidDataMapper.findByProject(project.getId());
        project.setBids(bids);
        return project;
    }

    public static void insert(Project project) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement =
                "INSERT OR IGNORE INTO project(id, title, description, imageUrl, budget, deadline, creationDate)" +
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
}
