import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarDAO implements DAO {
    private final static String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private final static String EXCEPTION_MESSAGE = "По данному пути файл не найдет!";
    private final static String EMPTY_TABLE_NAME = "Пустое имя таблицы!";
    private final static String EMPTY_TABLE = "Таблица пуста!";
    private final static String WRONG_ID_PATTERN = "Несуществующий ID!";
    private final static String CHOICE_OF_ID_PATTERN = "Пожалуйста, выберите один из приведенных ID: ";
    private final static String ID = "id";
    private final static String BRAND = "brand";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private String stringStatement;
    private PreparedStatement statement;
    private Connection connection;

    private String CREATE_PATTERN;
    private String READ_PATTERN;
    private String READ_ALL_PATTERN;
    private String ADD_PATTERN;
    private String UPDATE_PATTERN;
    private String DELETE_TABLE_PATTERN;
    private String DELETE_LINE_PATTERN;
    private String GET_ALL_ID_PATTERN;

    private Connection createConnection() throws SQLException {
        final Properties properties = new Properties();
        try (final FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(EXCEPTION_MESSAGE);
        }

        final String url = properties.getProperty("url");
        final String user = properties.getProperty("user");
        final String password = properties.getProperty("password");

        CREATE_PATTERN = properties.getProperty("createPattern");
        READ_PATTERN = properties.getProperty("readPattern");
        READ_ALL_PATTERN = properties.getProperty("readAllPattern");
        ADD_PATTERN = properties.getProperty("addPattern");
        UPDATE_PATTERN = properties.getProperty("updatePattern");
        DELETE_TABLE_PATTERN = properties.getProperty("deleteTablePattern");
        DELETE_LINE_PATTERN = properties.getProperty("deleteLinePattern");
        GET_ALL_ID_PATTERN = properties.getProperty("getAllIdPattern");

        return DriverManager.getConnection(url, user, password);
    }

    public void create(final String table) {
        try {
            connection = createConnection();
            stringStatement = String.format(DELETE_TABLE_PATTERN, table);
            statement = connection.prepareStatement(stringStatement);
            statement.execute();

            stringStatement = String.format(CREATE_PATTERN, table);
            statement = connection.prepareStatement(stringStatement);
            statement.execute();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            /*
            Что я должен здесь написать? Никак не соображу.
            Я должен в каждом методе обработать исключение, верно?
            Ну и тестовые сценарии никак не приходят в голову.
             */
        }
    }

    public Car read(final String table, final int id) throws SQLException {
        final List<Integer> idList = new ArrayList<>();
        final List<String> brandList = new ArrayList<>();
        final List<Integer> ageOfProduceList = new ArrayList<>();
        connection = createConnection();

        stringStatement = String.format(READ_PATTERN, table, id);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            idList.add(id);
            brandList.add(resultSet.getString(BRAND));
            ageOfProduceList.add(resultSet.getInt(AGE_OF_PRODUCE));
        }

        statement.close();
        connection.close();

        return new Car(idList, brandList, ageOfProduceList);
    }

    public Car readAll(final String table) throws SQLException {
        final List<Integer> idList = readAllId(table);
        final List<String> brandList = new ArrayList<>();
        final List<Integer> ageOfProduceList = new ArrayList<>();
        connection = createConnection();

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
        connection.close();

        return new Car(idList, brandList, ageOfProduceList);
    }

    private List<Integer> readAllId(final String table) throws SQLException {
        final List<Integer> idList = new ArrayList<>();
        connection = createConnection();

        stringStatement = String.format(GET_ALL_ID_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        final ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            idList.add(resultSet.getInt(ID));
        }

        statement.close();
        connection.close();

        return idList;
    }

    public void update(final String table, final String brand, final int ageOfProduce) throws SQLException {
        connection = createConnection();

        stringStatement = String.format(ADD_PATTERN, table, brand, ageOfProduce);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void update(final String table, final int id, final String column,
                       final String newValue) throws SQLException {

        connection = createConnection();

        stringStatement = String.format(UPDATE_PATTERN, table, id, column, newValue);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void deleteTable(final String table) throws SQLException {
        connection = createConnection();

        stringStatement = String.format(DELETE_TABLE_PATTERN, table);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
        connection.close();
    }

    public void delete(final String table, final int id) throws SQLException {
        connection = createConnection();

        stringStatement = String.format(DELETE_LINE_PATTERN, table, id);
        statement = connection.prepareStatement(stringStatement);
        statement.execute();

        statement.close();
        connection.close();
    }

    public boolean isIdPresent(final String table, final int id) throws SQLException {
        connection = createConnection();
        final List<Integer> idList = readAllId(table);
        connection.close();

        if (idList.contains(id)) {
            return true;
        } else {
            System.out.println(WRONG_ID_PATTERN);
            return false;
        }
    }

    public boolean isTableEmpty(final String table) throws SQLException {
        connection = createConnection();
        final List<Integer> idList = readAllId(table);
        connection.close();

        if (idList.size() == 0) {
            System.out.println(EMPTY_TABLE);
            return true;
        } else {
            return false;
        }
    }

    public void choiceOfId(final String table) throws SQLException {
        System.out.println(CHOICE_OF_ID_PATTERN);
        System.out.println(readAllId(table));
    }

    public boolean isTableNameEmpty(final String table) {
        if (table.isEmpty()) {
            System.out.println(EMPTY_TABLE_NAME);
            return true;
        } else {
            return false;
        }
    }
}
