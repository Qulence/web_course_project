package testUtilities;

import jdbcUtilities.JDBCConnectionUtil;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {

    public static void executeScript(String path, JDBCConnectionUtil jdbcConnectionUtil) {
        try (Connection connection = jdbcConnectionUtil.getConnection()) {
            ScriptRunner sr = new ScriptRunner(connection);
            sr.setLogWriter(null);
            Reader reader = new BufferedReader(new FileReader(path));
            sr.runScript(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
