

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.util.Map;

public class JdbcUtil {
    private final static String DEFAULT_DATABASE_NAME = "jdbc_app";
    private final static String DEFAULT_USERNAME = "root";
    private final static String DEFAULT_PASSWORD = "root";

    public static DataSource createDefaultInMemoryH2DataSource() {
        String url = formatMySqlDataSource();
        return createDefaultInMemoryH2DataSource(url, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    private static DataSource createDefaultInMemoryH2DataSource(String url, String username, String password) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setURL(url);
        return dataSource;
    }

    private static String formatMySqlDataSource() {
        return String.format("jdbc:mysql://localhost:3306/%s", DEFAULT_DATABASE_NAME);
    }

    public static Map<String, String> getInMemoryDbProperties() {
        return Map.of(
                "url", String.format("jdbc:h2:mem:%s", DEFAULT_DATABASE_NAME),
                "username", DEFAULT_USERNAME,
                "password", DEFAULT_PASSWORD
        );
    }


}
