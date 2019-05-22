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
    public static User find(int userId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM User WHERE id = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
            System.out.println(2);
            ResultSet rs = dbStatement.executeQuery();
            System.out.println(3);
            rs.next();
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

    public static List<User> getAll (int userId)
    {
        return getAll(null, false, userId);
    }

    public static boolean exists(int userId) {
        String statement = "SELECT EXISTS (SELECT * FROM User WHERE id = ?)";
        try {
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setInt(1, userId);
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

    public static boolean existUserName(String userName) {
        String statement = "SELECT EXISTS( SELECT * FROM User WHERE userName = ?)";
        try {
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, userName);
            ResultSet rs = dbStatement.executeQuery();
            rs.next();
            boolean ret = rs.getBoolean(1);
            rs.close();
            dbStatement.close();
            db.close();
            return ret;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException.SqlException();
        }
    }

    public static List<User> getAll(String searchKey, boolean exceptCurrent, int userId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM User";
            if (exceptCurrent)
                statement += " WHERE id <> " + Integer.toString(userId);
            if (exceptCurrent && searchKey != null)
                statement += " AND firstName || ' ' || lastName LIKE ?";
            else if (searchKey != null)
                statement += "WHERE firstName || ' ' || lastName LIKE ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            if (searchKey != null)
                dbStatement.setString(1, searchKey + "%");
            ResultSet rs = dbStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next())
                users.add(fillUser(rs));
            rs.close();
            dbStatement.close();
            db.close();
            return users;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static User fillUser(ResultSet rs) throws SQLException {
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String userName = rs.getString("userName");
        String jobTitle = rs.getString("jobTitle");
        String profilePicUrl = rs.getString("profilePicUrl");
        String bio = rs.getString("bio");
        int userId = rs.getInt("id");
        List<Skill> skills = UserSkillDataMapper.findByUser(userId);
        return new User(userId, firstName, lastName, userName, jobTitle, profilePicUrl, bio, skills);
    }

    public static void insert(User user) {
        try {
            Connection db = DataSource.getConnection();
            String statement =
                    "INSERT IGNORE INTO User(firstName, lastName, userName, password, jobTitle, profilePicUrl, bio)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, user.getFirstName());
            dbStatement.setString(2, user.getLastName());
            dbStatement.setString(3, user.getUserName());
            dbStatement.setString(4, user.getPassword());
            dbStatement.setString(5, user.getJobTitle());
            dbStatement.setString(6, user.getProfilePicUrl());
            dbStatement.setString(7, user.getBio());
            dbStatement.execute();
            dbStatement.close();
            db.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new CustomException.SqlException();
        }
        int userId = findIdByUserName(user.getUserName());
        for (Skill s : user.getSkills()) {
            UserSkillDataMapper.insert(userId, s.getName());
        }
    }

    private static int findIdByUserName(String userName) {
        String statement = "SELECT id FROM User WHERE userName = ?";
        try {
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, userName);
            ResultSet rs = dbStatement.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            rs.close();
            dbStatement.close();
            db.close();
            return id;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static int findByUsernamePass(String userName, String password) {
        String statement = "SELECT id FROM User WHERE userName = ? and password = ?";
        try {
            System.out.println(password);
            Connection db = DataSource.getConnection();
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, userName);
            dbStatement.setString(2, password);
            ResultSet rs = dbStatement.executeQuery();
            int id = 0;
            if(rs.next())
                id = rs.getInt(1);
            System.out.println(id);
            rs.close();
            dbStatement.close();
            db.close();
            return id;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }
}
