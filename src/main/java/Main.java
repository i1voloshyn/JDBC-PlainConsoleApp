import javax.sql.DataSource;
import java.sql.*;

public class Main {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE message(
            body VARCHAR(255), creation_date TIMESTAMP DEFAULT now()
            )
            """;

    private static final String INSERT_SQL = """
            INSERT INTO message(body) VALUES(?)
            """;

    private static final String SELECT_ALL = "SELECT * FROM message ";

    private static DataSource dataSource;


    public static void main(String[] args) throws SQLException {
        init();
    }

    private static void init() {
        dataSource = JdbcUtil.createDefaultInMemoryH2DataSource();
    }

    private static void createMessageTable() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(CREATE_TABLE_SQL);
        }
    }

    private static void saveSomeMessagesIntoDB() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement insertStatement = connection.prepareStatement(INSERT_SQL);
            insertSomeMessages(insertStatement);
        }
    }

    private static void insertSomeMessages(PreparedStatement insertStatement) throws SQLException {
        insertStatement.setString(1, "Hello!");
        insertStatement.executeUpdate();

        insertStatement.setString(1, "How are you?");
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
