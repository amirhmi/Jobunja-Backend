package DataAccess;

import Model.Entity.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserSkillDataMapper {
    public static void insert(String userId, String skillName) throws SQLException {
        String statement = "INSERT OR IGNORE INTO UserSkill(userid, skillName)" +
                            "VALUES(?, ?)";
        Connection db = DataSource.getConnection();
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, userId);
        dbStatement.setString(2, skillName);
        dbStatement.execute();
        dbStatement.close();
        db.close();
    }

    public static List<Skill> findByUser(String userId) throws SQLException {
        String statement = "SELECT * FROM UserSkill U WHERE U.userId = ?";
        Connection db = DataSource.getConnection();
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, userId);
        ResultSet rs = dbStatement.executeQuery();
        List<Skill> skills = new ArrayList<>();
        while (rs.next()) {
            Skill skill = new Skill();
            String skillName = rs.getString("skillName");
            skill.setName(skillName);
            List<String> endorsersId = EndorsementDataMapper.findEndorsersId(userId, skillName);
            skill.setEndorsedBy(endorsersId);
            skills.add(skill);
        }
        dbStatement.close();
        db.close();
        return skills;
    }
}
