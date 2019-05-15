package DataAccess;

import Exception.CustomException;
import Model.Entity.Bid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BidDataMapper {
    public static void insert(String projectId, String userId, int budget) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "INSERT INTO bid(userId, projectId, budget) VALUES(?, ?, ?)";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, userId);
            dbStatement.setString(2, projectId);
            dbStatement.setInt(3, budget);
            dbStatement.execute();
            dbStatement.close();
            db.close();
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static Bid find(String userId, String projectId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM bid WHERE userId = ? and projectId = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, userId);
            dbStatement.setString(2, projectId);
            ResultSet rs = dbStatement.executeQuery();
            Bid bid = null;
            if(rs.next())
                bid = fillBid(rs);
            rs.close();
            dbStatement.close();
            db.close();
            return bid;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static List<Bid> findByProject(String projectId) {
        try {
            Connection db = DataSource.getConnection();
            String statement = "SELECT * FROM bid WHERE projectId = ?";
            PreparedStatement dbStatement = db.prepareStatement(statement);
            dbStatement.setString(1, projectId);
            ResultSet rs = dbStatement.executeQuery();
            List<Bid> bids = new ArrayList<>();
            while (rs.next())
                bids.add(fillBid(rs));
            rs.close();
            dbStatement.close();
            db.close();
            return bids;
        }
        catch (SQLException e) {
            throw new CustomException.SqlException();
        }
    }

    public static Bid fillBid(ResultSet rs) throws SQLException {
        String userId = rs.getString("userId");
        String projectId = rs.getString("projectId");
        int budget = rs.getInt("budget");
        return new Bid(userId, budget, projectId);
    }
}
