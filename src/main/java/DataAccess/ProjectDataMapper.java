package DataAccess;

import Model.Entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDataMapper {

    public static Project find(String projectId) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "SELECT * FROM project WHERE id = ?";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, projectId);
        ResultSet rs = dbStatement.executeQuery();
        Project project = new Project();
        return null;

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
