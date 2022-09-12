package dao;

import dao.Car;
import dao.CarDAO;
import dao.DAO;
import factory.ConnectionFactory;
import factory.StatementFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarDAOImpl implements CarDAO {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private final static String FILE_NOT_FOUND_MESSAGE = "По данному пути файл не найдет!";
    private final static String GET_VALUES_ERROR_MESSAGE = "Ошибка при получении значений!";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String ID = "id";
    private final static String BRAND = "brand";
    private final static String MODEL = "model";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String PRICE = "price";

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
            System.out.println(FILE_NOT_FOUND_MESSAGE);
        }

        READ_PATTERN = properties.getProperty("readPattern");
        READ_ALL_PATTERN = properties.getProperty("readAllPattern");
        ADD_PATTERN = properties.getProperty("addPattern");
        UPDATE_PATTERN = properties.getProperty("updatePattern");
        DELETE_TABLE_PATTERN = properties.getProperty("deleteTablePattern");
        DELETE_LINE_PATTERN = properties.getProperty("deleteLinePattern");
        GET_ALL_ID_PATTERN = properties.getProperty("getAllIdPattern");
    }

    public void createTable() {
        new DAO();
    }

    public Car read(final int id) {
        final Car car = new Car();

        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(READ_PATTERN, id);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);

        final ResultSet resultSet;
        try {
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                car.setId(id);
                car.setModel(resultSet.getString(MODEL));
                car.setBrand(resultSet.getString(BRAND));
                car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                car.setPrice(resultSet.getInt(PRICE));
            }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);

        return car;
    }

    public List<Car> readAll() {
        final List<Car> carList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(READ_ALL_PATTERN);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);

        try {
                final ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    final Car car = new Car();
                    car.setId(resultSet.getInt(ID));
                    car.setBrand(resultSet.getString(BRAND));
                    car.setModel(resultSet.getString(MODEL));
                    car.setAgeOfProduce(resultSet.getInt(AGE_OF_PRODUCE));
                    car.setPrice(resultSet.getInt(PRICE));
                    carList.add(car);
                }
        } catch (SQLException e) {
            System.out.println(GET_VALUES_ERROR_MESSAGE);
        }

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);

        return carList;
    }

    private List<Integer> readAllId() {
        final List<Integer> idList = new ArrayList<>();

        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(GET_ALL_ID_PATTERN);
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

    public void save(final String brand, final String model, final int ageOfProduce, final int price) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(ADD_PATTERN, brand, model, ageOfProduce, price);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void update(final int id, final int newPrice) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(UPDATE_PATTERN, newPrice, id);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void deleteTable() {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(DELETE_TABLE_PATTERN);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void delete(final int id) {
        final Connection connection = connectionFactory.createConnection();

        final String stringStatement = String.format(DELETE_LINE_PATTERN, id);
        final PreparedStatement statement = statementFactory.prepareStatement(connection, stringStatement);
        statementFactory.executeStatement(statement);

        statementFactory.closeStatement(statement);
        connectionFactory.closeConnection(connection);
    }

    public void choiceOfId() {
        System.out.println(CHOICE_OF_ID_PATTERN + "\n" + readAllId());
    }
}
