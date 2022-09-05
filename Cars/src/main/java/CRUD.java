import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUD {
    private final static String CREATE_PATTERN = "CREATE TABLE IF NOT EXISTS %s(id SERIAL PRIMARY KEY, brand VARCHAR(30), age_of_produce INT);";
    private final static String READ_PATTERN = "SELECT * FROM %s;";
    private final static String UPDATE_PATTERN_1 = "INSERT INTO %s(brand, age_of_produce) VALUES(\'%s\', %d)";
    private final static String UPDATE_PATTERN_2 = "UPDATE %1$s SET %2$s = \'%4$s\' WHERE %2$s = \'%3$s\'";
    private final static String DELETE_PATTERN_1 = "DROP TABLE IF EXISTS %s;";
    private final static String DELETE_PATTERN_2 = "DELETE FROM %s WHERE id = \'%d\';";
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

    public void update(final Connection connection, final String table, final String brand,
                       final int ageOfProduce) throws SQLException {

        stringStatement = String.format(UPDATE_PATTERN_1, table, brand, ageOfProduce);
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

    public void delete(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(DELETE_PATTERN_1, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void delete(final Connection connection, final String table, final int id) throws SQLException {
        stringStatement = String.format(DELETE_PATTERN_2, table, id);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }
}
