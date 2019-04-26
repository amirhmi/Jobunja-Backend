package DataAccess;

import Model.Entity.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDataMapper {
    public static boolean find(String skillName) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "SELECT * FROM Skill WHERE skillName = ?";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, skillName);
        ResultSet rs = dbStatement.executeQuery();
        String retSkillName = rs.getString("skillName");
        rs.close();
        dbStatement.close();
        db.close();
        if(retSkillName == skillName)
            return true;
        else
            return false;
    }

    public static void insert(String skillName) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "INSERT INTO Skill VALUES(?)";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, skillName);
        dbStatement.execute();
        dbStatement.close();
        db.close();
    }

    public static List<String> getAll() throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "SELECT * FROM Skill";
        Statement stmt = db.createStatement();
        ResultSet rs = stmt.executeQuery(statement);
        List<String> skills = new ArrayList<>();
        while (rs.next())
            skills.add(rs.getString("skillName"));
        rs.close();
        stmt.close();
        db.close();
        return skills;
    }
}
