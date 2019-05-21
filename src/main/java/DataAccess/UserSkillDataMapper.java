package DataAccess;

import Model.Entity.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Exception.CustomException;

public class UserSkillDataMapper {
    public static void insert(int userId, String skillName) {
        String statement = "INSERT IGNORE INTO UserSkill(userid, skillName)" +
                            "VALUES(?, ?)";
        try {
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
            dbStatement.setString(2, skillName);
            dbStatement.execute();
            dbStatement.close();
            db.close();
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static void delete(int userId, String skillName) throws CustomException.SqlException {
        try {
            Connection db = DataSource.getConnection();
            String statement = "DELETE FROM userSkill WHERE userId = ? and skillName = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
            dbStatement.setString(2, skillName);
            dbStatement.execute();
            dbStatement.close();
            db.close();
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static boolean exists(String skillname, int userId) {
        String statement = "SELECT CASE WHEN EXISTS (SELECT * FROM UserSkill U WHERE U.userId = ? AND U.skillName = ?) "
                + "THEN CAST (1 AS BIT) ELSE CAST (0 AS BIT) END";
        try {
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
            dbStatement.setString(2, skillname);
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

    public static List<String> findNotExistsForUser(int userId) {
        String statement = "SELECT S.skillName FROM skill S " +
                "WHERE NOT EXISTS (SELECT * FROM userSkill US WHERE US.userId = ? and US.skillName = S.skillName)";
        try {
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
            ResultSet rs = dbStatement.executeQuery();
            rs.next();
            List<String> skills = new ArrayList<>();
            while (rs.next())
                skills.add(rs.getString("skillName"));
            rs.close();
            dbStatement.close();
            db.close();
            return skills;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
    }
}

    public static List<Skill> findByUser(int userId) throws SQLException {
        String statement = "SELECT * FROM UserSkill U WHERE U.userId = ?";
        Connection db = DataSource.getConnection();
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setInt(1, userId);
        ResultSet rs = dbStatement.executeQuery();
        rs.next();
        List<Skill> skills = new ArrayList<>();
        while (rs.next()) {
            Skill skill = new Skill();
            String skillName = rs.getString("skillName");
            skill.setName(skillName);
            List<Integer> endorsersId = EndorsementDataMapper.findEndorsersId(userId, skillName);
            skill.setEndorsedBy(endorsersId);
            skills.add(skill);
        }
        rs.close();
        dbStatement.close();
        db.close();
        return skills;
    }
}
