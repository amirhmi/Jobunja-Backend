package DataAccess;

import Model.Entity.Skill;
import Model.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDataMapper {
    public static User find(String userId) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "SELECT * FROM user WHERE id = ?";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, userId);
        ResultSet rs = dbStatement.executeQuery();
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String jobTitle = rs.getString("jobTitle");
        String profilePicUrl = rs.getString("profilePicUrl");
        String bio = rs.getString("bio");
        List<Skill> skills = UserSkillDataMapper.findByUser(userId);
        System.out.println(firstName + " " + lastName + " " + jobTitle + " " + profilePicUrl + " " + bio);
        for(Skill s : skills)
            System.out.println(s.getName());
        return new User(userId, firstName, lastName, skills, jobTitle, profilePicUrl, bio);

    }

    public static void insert(User user) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement =
                "INSERT OR IGNORE INTO User(id, firstName, lastName, jobTitle, profilePicUrl, bio)" +
                        "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, user.getId());
        dbStatement.setString(2, user.getFirstName());
        dbStatement.setString(3, user.getLastName());
        dbStatement.setString(4, user.getJobTitle());
        dbStatement.setString(5, user.getProfilePicUrl());
        dbStatement.setString(6, user.getBio());
        dbStatement.execute();
        dbStatement.close();
        db.close();
        for (Skill s : user.getSkills()) {
            UserSkillDataMapper.insert(user.getId(), s.getName());
        }
    }
}
