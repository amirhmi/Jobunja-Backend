package DataAccess;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        File dbfile = new File(".");
        ds.setDriverClassName("org.sqlite.JDBC");
        System.out.println(dbfile.getAbsolutePath());
        ds.setUrl("jdbc:sqlite:" + dbfile.getAbsolutePath() + "/jobunja.db");
        ds.setMinIdle(2);
        ds.setMaxIdle(5);
        ds.setMaxOpenPreparedStatements(2);
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(10);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void executeSql(String sql) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            connection.close();
            statement.close();
            System.out.println("saaal");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private DataSource(){ }
}