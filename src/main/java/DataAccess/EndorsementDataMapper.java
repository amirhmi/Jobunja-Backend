package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EndorsementDataMapper {
    public static void insert(String endorserId, String endorsedId, String skillName) throws SQLException {
        Connection db = DataSource.getConnection();
        String statement = "INSERT INTO endorsement(endorserId, endorsedId, skillName) VALUES(?, ?, ?)";
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, endorserId);
        dbStatement.setString(2, endorsedId);
        dbStatement.setString(3, skillName);
        dbStatement.execute();
        dbStatement.close();
        db.close();
    }

    public static List<String> findEndorsersId(String endorsedId, String skillName) throws SQLException {
        String statement = "SELECT endorserId FROM endorsement WHERE endorsedId = ? and skillName = ?";
        Connection db = DataSource.getConnection();
        PreparedStatement dbStatement = db.prepareStatement(statement);
        dbStatement.setString(1, endorsedId);
        dbStatement.setString(2, skillName);
        ResultSet rs = dbStatement.executeQuery();
        List<String> endorsersId = new ArrayList<>();
        while (rs.next())
            endorsersId.add(rs.getString("endorserId"));
        rs.close();
        dbStatement.close();
        db.close();
        return endorsersId;
    }
}
