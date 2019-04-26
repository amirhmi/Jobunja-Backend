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
        ds.setMinIdle(5);
        ds.setMaxIdle(50);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void excuteSql(String sql) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            connection.close();
            statement.close();
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private DataSource(){ }
}