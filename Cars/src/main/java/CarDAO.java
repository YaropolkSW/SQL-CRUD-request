import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAO implements DAO {
    private final static String CREATE_PATTERN = "CREATE TABLE IF NOT EXISTS %s(id SERIAL PRIMARY KEY, brand VARCHAR(30) NOT NULL, age_of_produce INT NOT NULL);";
    private final static String READ_PATTERN = "SELECT * FROM %s WHERE id = %d;";
    private final static String READ_ALL_PATTERN = "SELECT * FROM %s WHERE id = (?)";
    private final static String ADD_PATTERN = "INSERT INTO %s(brand, age_of_produce) VALUES(\'%s\', %d)";////////////////////////
    private final static String UPDATE_PATTERN = "UPDATE %1$s SET %3$s = \'%4$s\' WHERE id = %2$d";
    private final static String DELETE_TABLE_PATTERN = "DROP TABLE IF EXISTS %s;";
    private final static String DELETE_LINE_PATTERN = "DELETE FROM %s WHERE id = %d;";
    private final static String GET_ALL_ID_PATTERN = "SELECT id FROM %s";
    private final static String WRONG_ID_PATTERN = "Несуществующий ID!";
    private final static String EMPTY_TABLE = "Таблица пуста!";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String ID = "id";
    private final static String BRAND = "brand";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private String stringStatement;
    private PreparedStatement statement;

    public void create(final Connection connection, final String table) throws SQLException {
        stringStatement = String.format(DELETE_TABLE_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        stringStatement = String.format(CREATE_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public DataTransfer read(final Connection connection, final String table, final int id) throws SQLException {
        final List<Integer> idList = new ArrayList<>();
        final List<String> brandList = new ArrayList<>();
        final List<Integer> ageOfProduceList = new ArrayList<>();

        stringStatement = String.format(READ_PATTERN, table, id);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            idList.add(id);
            brandList.add(resultSet.getString(BRAND));
            ageOfProduceList.add(resultSet.getInt(AGE_OF_PRODUCE));
        }

        return new DataTransfer(idList, brandList, ageOfProduceList);
    }

    public DataTransfer readAll(final Connection connection, final String table) throws SQLException {
        final List<Integer> idList = readAllId(connection, table);
        final List<String> brandList = new ArrayList<>();
        final List<Integer> ageOfProduceList = new ArrayList<>();

        stringStatement = String.format(READ_ALL_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);

        for (final Integer element : idList) {
            statement.setInt(1, element);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                brandList.add(resultSet.getString(BRAND));
                ageOfProduceList.add(resultSet.getInt(AGE_OF_PRODUCE));
            }
        }

        statement.close();

        return new DataTransfer(idList, brandList, ageOfProduceList);
    }

    private List<Integer> readAllId(final Connection connection, final String table) throws SQLException {
        final List<Integer> idList = new ArrayList<>();

        stringStatement = String.format(GET_ALL_ID_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            idList.add(resultSet.getInt(ID));
        }

        return idList;
    }

    public void update(final Connection connection, final String table, final String brand,
                       final int ageOfProduce) throws SQLException {

        stringStatement = String.format(ADD_PATTERN, table, brand, ageOfProduce);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
    }

    public void update(final Connection connection, final String table, final int id, final String column,
                       final String newValue) throws SQLException {

        stringStatement = String.format(UPDATE_PATTERN, table, id, column, newValue);
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

    public boolean isIdPresent(final Connection connection, final String table, final int id) throws SQLException {
        final List<Integer> idList = readAllId(connection, table);

        if (idList.contains(id)) {
            return true;
        } else {
            System.out.println(WRONG_ID_PATTERN);
            return false;
        }
    }

    public boolean isTableEmpty(final Connection connection, final String table) throws SQLException {
        final List<Integer> idList = readAllId(connection, table);

        if (idList.size() == 0) {
            System.out.println(EMPTY_TABLE);
            return true;
        } else {
            return false;
        }
    }

    public void choiceOfId(final Connection connection, final String table) throws SQLException {
        System.out.println(CHOICE_OF_ID_PATTERN);
        System.out.println(readAllId(connection, table));
    }
}
