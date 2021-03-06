package DataAccess;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://185.166.107.141:30037/tracker?useUnicode=yes&characterEncoding=UTF-8");
        ds.setUsername("root");
        ds.setPassword("sheep");
        ds.setMinIdle(2);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(10);
        ds.setRemoveAbandoned(true);
        ds.setRemoveAbandonedTimeout(10);
    }

    public static Connection getConnection() {
        Connection connection = null;

        while (connection == null) {
            try {
                connection = ds.getConnection();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("Connecting failed, retrying...");
            }
        }
        return connection;
//        return ds.getConnection();
    }

    public static void executeSql(String sql) {
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