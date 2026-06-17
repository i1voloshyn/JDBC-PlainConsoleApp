import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public  class MySqlJdbcUtil {
    private static final String DEFAULT_DATABASE_NAME = "post";
    private static final String DEFAULT_USER_NAME = "root";
    private static final String DEFAULT_PASSWORD = "root";


    public static DataSource createDatasource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        return createDefaultMySqlDataSource(dataSource);
    }

    private static DataSource createDefaultMySqlDataSource(MysqlDataSource dataSource) {
        dataSource.setURL(formatMySqlDataSource());
        dataSource.setUser(DEFAULT_USER_NAME);
        dataSource.setPassword(DEFAULT_PASSWORD);
        return dataSource;
    }

    private static String formatMySqlDataSource() {
        return String.format("jdbc:mysql://localhost:3306/%s", DEFAULT_DATABASE_NAME);
    }

}
