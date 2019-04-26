package DataAccess;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        File dbfile = new File(".");
        ds.setDriverClassName("org.sqlite.JDBC");
        System.out.println(dbfile.getAbsolutePath());
        ds.setUrl("jdbc:sqlite:" + dbfile.getAbsolutePath() + "jobunja.db");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource(){ }
}