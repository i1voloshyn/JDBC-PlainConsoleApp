import javax.sql.DataSource;
import java.sql.*;

public class Main {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS posts(
            body VARCHAR(255), fromWho VARCHAR(255), creation_date TIMESTAMP DEFAULT now()
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO posts(body, fromWho) VALUES(?,?)
            """;

    private static final String SELECT_ALL = "SELECT * FROM message ";

    private static DataSource dataSource;


    public static void main(String[] args) throws SQLException {
        init();
        createPostsTable();
        savePostsIntoDB();
    }

    private static void init() {
        dataSource = MySqlJdbcUtil.createDatasource();
    }

    private static void createPostsTable() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        }
    }

    private static void savePostsIntoDB() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL);
            insertSomeMessages(insertStatement);
        }
    }

    private static void insertSomeMessages(PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(1,"To Drugi Post" );
        insertStatement.setString(2,"Ivan");
        insertStatement.executeUpdate();

        insertStatement.setString(1, "Utworzylem DataSource");
        insertStatement.setString(2, "John");
        insertStatement.executeUpdate();
    }

    private static void printMessageFromDb() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SELECT_ALL);

            while (set.next()) {
                String message = set.getString(1);
                String date = set.getString(2);
                System.out.println(" - " + message + "[" + date + "]");
            }
        }
    }

}
