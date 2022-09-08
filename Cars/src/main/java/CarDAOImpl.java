import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarDAOImpl implements CarDAO {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private final static String EXCEPTION_MESSAGE = "По данному пути файл не найдет!";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String ID = "id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";

    private final String CREATE_TABLE_PATTERN;
    private final String READ_PATTERN;
    private final String READ_ALL_PATTERN;
    private final String ADD_PATTERN;
    private final String UPDATE_PATTERN;
    private final String DELETE_TABLE_PATTERN;
    private final String DELETE_LINE_PATTERN;
    private final String GET_ALL_ID_PATTERN;

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final StatementFactory statementFactory = new StatementFactory();

    {
        final Properties properties = new Properties();
        try (final FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(EXCEPTION_MESSAGE);
        }

        CREATE_TABLE_PATTERN = properties.getProperty("createTablePattern");
        READ_PATTERN = properties.getProperty("readPattern");
        READ_ALL_PATTERN = properties.getProperty("readAllPattern");
        ADD_PATTERN = properties.getProperty("addPattern");
        UPDATE_PATTERN = properties.getProperty("updatePattern");
        DELETE_TABLE_PATTERN = properties.getProperty("deleteTablePattern");
        DELETE_LINE_PATTERN = properties.getProperty("deleteLinePattern");
        GET_ALL_ID_PATTERN = properties.getProperty("getAllIdPattern");
    }

    public void create(final String table) {
        final Connection connection = connectionFactory.createConnection();

        String stringStatement = String.format(DELETE_TABLE_PATTERN, table);
        PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        String stringStatement2 = String.format(CREATE_TABLE_PATTERN, table);
        PreparedStatement statement2 = statementFactory.prepareStatement(connection, stringStatement2);
        statementFactory.executeStatement(statement2);

        statementFactory.closeStatement(statement);
        statementFactory.closeStatement(statement2);
        connectionFactory.closeConnection(connection);
    }

    public Car read(final String table, final int id) {
        final Car car = new Car();

        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(READ_PATTERN, table, id);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);

        final ResultSet resultSet;
        try {
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car.setId(id);
                car.setModel(resultSet.getString(MODEL));
                car.setBrand(resultSet.getString(BRAND));
                car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);

        return car;
    }

    public List<Car> readAll(final String table) {
        final List<Car> carList = new ArrayList<>();
        final List<Integer> idList = readAllId(table);

        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(READ_ALL_PATTERN, table);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);

        try {
            for (final Integer element : idList) {
                statement.setInt(1, element);
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final Car car = new Car();
                    car.setId(element);
                    car.setBrand(resultSet.getString(BRAND));
                    car.setModel(resultSet.getString(MODEL));
                    car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                    carList.add(car);
                }
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);

        return carList;
    }

    private List<Integer> readAllId(final String table) {
        final List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(GET_ALL_ID_PATTERN, table);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);

        final ResultSet resultSet;
        try {
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                idList.add(resultSet.getInt(ID));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);

        return idList;
    }

    public void update(final String table, final String brand, final String model, final int ageOfProduce) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(ADD_PATTERN, table, brand, model, ageOfProduce);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void update(final String table, final int id, final String column, final String newValue) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(UPDATE_PATTERN, table, id, column, newValue);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void deleteTable(final String table) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(DELETE_TABLE_PATTERN, table);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void delete(final String table, final int id) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(DELETE_LINE_PATTERN, table, id);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void choiceOfId(final String table) {
        System.out.println(CHOICE_OF_ID_PATTERN + "\n" + readAllId(table));
    }
}
