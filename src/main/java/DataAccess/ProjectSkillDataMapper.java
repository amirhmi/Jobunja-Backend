package DataAccess;

import Model.Entity.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectSkillDataMapper {
    public static List<Skill> findByProject(String projectId) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "SELECT * FROM ProjectSkill WHERE projectId = ?";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, projectId);
        ResultSet rs = dbStatement.executeQuery();
        List<Skill> skills = new ArrayList<>();
        while (rs.next()) {
            Skill skill = new Skill(rs.getString("skillName"), rs.getInt("value"));
            skills.add(skill);
        }
        rs.close();
        dbStatement.close();
        db.close();
        return skills;
    }

    public static void insert(String projectId, String skillName, int val) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "INSERT OR IGNORE INTO ProjectSkill(point, skillName, projectId) VALUES(?, ?, ?)";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setInt(1, val);
        dbStatement.setString(2, skillName);
        dbStatement.setString(3, projectId);
        dbStatement.execute();
        dbStatement.close();
        db.close();
    }
}