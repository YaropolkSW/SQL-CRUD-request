import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDAO {
    private final static String CREATE_PATTERN = "CREATE TABLE IF NOT EXISTS %s(id SERIAL PRIMARY KEY, brand VARCHAR(30), age_of_produce INT);";
    private final static String READ_PATTERN = "SELECT * FROM %s WHERE id = %d;";
    private final static String ADD_PATTERN = "INSERT INTO %s(brand, age_of_produce) VALUES(\'%s\', %d)";
    private final static String UPDATE_PATTERN = "UPDATE %1$s SET %2$s = \'%4$s\' WHERE %2$s = \'%3$s\'";
    private final static String DELETE_TABLE_PATTERN = "DROP TABLE IF EXISTS %s;";
    private final static String DELETE_LINE_PATTERN = "DELETE FROM %s WHERE id = \'%d\';";
    private final static String OUTPUT_PATTERN = "ID - %d\nBrand - %s\nAge of produce - %d\n";
    private final static String BRAND = "brand";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private PreparedStatement statement;
    private String stringStatement;

    public void create(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(CREATE_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void read(final Connection connection, final String table, final int id) throws SQLException {
        stringStatement = String.format(READ_PATTERN, table, id);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            final String brand = resultSet.getString(BRAND);
            final int ageOfProduce = resultSet.getInt(AGE_OF_PRODUCE);
            System.out.print(String.format(OUTPUT_PATTERN, id, brand, ageOfProduce));
        }

        statement.close();
    }

    public void update(final Connection connection, final String table, final String brand,
                       final int ageOfProduce) throws SQLException {

        stringStatement = String.format(ADD_PATTERN, table, brand, ageOfProduce);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void update(final Connection connection, final String table, final String column, final String oldValue,
                       final String newValue) throws SQLException {

        stringStatement = String.format(UPDATE_PATTERN, table, column, oldValue, newValue);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void delete(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(DELETE_TABLE_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void delete(final Connection connection, final String table, final int id) throws SQLException {
        stringStatement = String.format(DELETE_LINE_PATTERN, table, id);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }
}
