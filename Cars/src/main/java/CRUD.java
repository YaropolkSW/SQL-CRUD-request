import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUD {
    private final static String CREATE_PATTERN = "CREATE TABLE IF NOT EXISTS %s(id INT, brand VARCHAR(30), age_of_produce INT);";
    private final static String READ_PATTERN = "SELECT * FROM %s;";
    private final static String UPDATE_PATTERN_1 = "INSERT INTO %s(id, brand, age_of_produce) VALUES(%d, \'%s\', %d)";
    private final static String UPDATE_PATTERN_2 = "UPDATE %1$s SET %2$s = \'%4$s\' WHERE %2$s = \'%3$s\'";
    private final static String UPDATE_PATTERN_3 = "UPDATE %1$s SET %2$s = %4$d WHERE %2$s = %3$d";
    private final static String DELETE_PATTERN_1 = "DROP TABLE IF EXISTS %s;";
    private final static String DELETE_PATTERN_2 = "DELETE FROM %s WHERE brand = \'%s\';";
    private PreparedStatement statement;
    private String stringStatement;

    public void create(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(CREATE_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void read(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(READ_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void update(final Connection connection, final String table, final int id, final String brand,
                       final int ageOfProduce) throws SQLException {

        stringStatement = String.format(UPDATE_PATTERN_1, table, id, brand, ageOfProduce);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void update(final Connection connection, final String table, final String column, final String oldValue,
                       final String newValue) throws SQLException {

        stringStatement = String.format(UPDATE_PATTERN_2, table, column, oldValue, newValue);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void update(final Connection connection, final String table, final String column, final int oldValue,
                       final int newValue) throws SQLException {

        stringStatement = String.format(UPDATE_PATTERN_3, table, column, oldValue, newValue);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void delete(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(DELETE_PATTERN_1, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void delete(final Connection connection, final String table, final String brand) throws SQLException {
        stringStatement = String.format(DELETE_PATTERN_2, table, brand);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }
}
