package DataAccess;

import Model.Entity.Skill;
import Model.Entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataMapper {
    public static User find(String userId) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "SELECT * FROM user WHERE id = ?";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, userId);
        ResultSet rs = dbStatement.executeQuery();
        while (rs.next())
            System.out.println(rs.getType());
        //User user = new User(userId, );
        return null;

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
        statement = "INSERT OR IGNORE INTO UserSkill(userid, skillName)" +
                            "VALUES(?, ?)";
        for (Skill s : user.getSkills()) {
            db = DataSource.getConnection();
            dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, user.getId());
            dbStatement.setString(2, s.getName());
            dbStatement.execute();
            dbStatement.close();
            db.close();
        }
    }
}
