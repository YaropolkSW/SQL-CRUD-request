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
    private final static String ADD_PATTERN = "INSERT INTO %s(brand, age_of_produce) VALUES(\'%s\', %d)";
    private final static String UPDATE_PATTERN = "UPDATE %1$s SET %3$s = %4$s WHERE id = %2$d";
    private final static String DELETE_TABLE_PATTERN = "DROP TABLE IF EXISTS %s;";
    private final static String DELETE_LINE_PATTERN = "DELETE FROM %s WHERE id = \'%d\';";
    private final static String GET_ALL_ID_PATTERN = "SELECT id FROM %s";
    private final static String WRONG_ID_PATTERN = "Несуществующий ID!";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String OUTPUT_PATTERN = "ID - %d\nBrand - %s\nAge of produce - %d\n";
    private final static String STRING_SEPARATOR = "------------------------------";
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

        stringStatement = String.format(READ_PATTERN, table, id);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();
        String brand = "";
        int ageOfProduce = 0;

        if (resultSet.next()) {
            brand = resultSet.getString(BRAND);
            ageOfProduce = resultSet.getInt(AGE_OF_PRODUCE);
        }

        return new DataTransfer(id, brand, ageOfProduce);
    }
////////////////////////////////////////// TEST //////////////////////////////////////////
    public DataTransfer readAll(final Connection connection, final List<Integer> listOfID,
                        final String table) throws SQLException {
        final List<String> brandList = new ArrayList<>();
        final List<Integer> ageOfProduceList = new ArrayList<>();

        stringStatement = String.format(READ_ALL_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);

        for (final Integer element : listOfID) {
            statement.setInt(1, element);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                brandList.add(resultSet.getString(BRAND));
                ageOfProduceList.add(resultSet.getInt(AGE_OF_PRODUCE));
            }
        }

        statement.close();

        return new DataTransfer(listOfID, brandList, ageOfProduceList);
    }

    public List<Integer> readAllID(final Connection connection, final String table) throws SQLException {
        final List<Integer> listOfID = new ArrayList<>();

        stringStatement = String.format(GET_ALL_ID_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            listOfID.add(resultSet.getInt(ID));
        }

        if (listOfID.size() == 0) {
            return listOfID;
        }

        System.out.print(CHOICE_OF_ID_PATTERN);
        System.out.println(listOfID);

        return listOfID;
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

    public boolean isIdPresent(final List<Integer> listOfID, final int id) {
        if (!listOfID.contains(id)) {
            System.out.println(WRONG_ID_PATTERN);
            return false;
        } else {
            return true;
        }
    }
}
