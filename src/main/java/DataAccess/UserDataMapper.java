package DataAccess;

import Model.Entity.Skill;
import Model.Entity.User;
import Exception.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataMapper {
    public static User find(String userId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM user WHERE id = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, userId);
            ResultSet rs = dbStatement.executeQuery();
            User user = fillUser(rs);
            rs.close();
            dbStatement.close();
            db.close();
            return user;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static List<User> getAll() {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM user";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            ResultSet rs = dbStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next())
                users.add(fillUser(rs));
            return users;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static User fillUser(ResultSet rs) throws SQLException {
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String jobTitle = rs.getString("jobTitle");
        String profilePicUrl = rs.getString("profilePicUrl");
        String bio = rs.getString("bio");
        String userId = rs.getString("id");
        List<Skill> skills = UserSkillDataMapper.findByUser(userId);
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
