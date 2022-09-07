import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class UI {
    private final static String MESSAGE = "1 для добавления таблицы\n" +
                                          "2 для просмотра таблицы\n" +
                                          "3 для добавления или изменения строки\n" +
                                          "4 для удаления таблицы или строки\n" +
                                          "5 для выхода\n" +
                                          "Введите запрос: ";
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;
    private final static int FOUR = 4;
    private final static int FIVE = 5;
    private final static String REQUEST_TABLE_NAME = "Введите имя таблицы: ";
    private final static String REQUEST_COLUMN_NAME = "Введите название столбца: " ;
    private final static String REQUEST_ID = "Введите id: ";
    private final static String REQUEST_BRAND = "Введите брэнд: ";
    private final static String REQUEST_AGE_OF_PRODUCE = "Введите год изготовления: ";
    private final static String UPDATE_MESSAGE_CHOICE = "Введите 1 чтобы добавить строку или 2, чтобы изменить строку: ";
    private final static String DELETE_CHOICE_MESSAGE = "Введите 1 чтобы удалить таблицу или 2, чтобы удалить строку: ";
    private final static String REQUEST_NEW_VALUE = "Введите новое значение: ";
    private final static String READ_MESSAGE_CHOICE = "Введите 1 чтобы прочитать одну строку или 2, чтобы прочитать всю таблицу: ";
    private final static String INCORRECT_REQUEST = "Неверный запрос, введите корректный запрос!";
    private final static String EMPTY_TABLE = "Таблица пуста!";
    private final static String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private final static String EXCEPTION_MESSAGE = "По данному пути файл не найдет!";

    private final static Scanner scanner = new Scanner(System.in);
    private final CarDAO carDAO = new CarDAO();

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

        return DriverManager.getConnection(url, user, password);
    }

    public boolean userInterface() throws SQLException {
        final Connection connection = createConnection();
        final Printer printer = new Printer();
        int id;
        int console;
        String table;
        List<Integer> listOfID;
        final DataTransfer dataTransferObject;

        getMessage();

        switch (Integer.parseInt(scanner.nextLine())) {
            case ONE:
                System.out.print(REQUEST_TABLE_NAME);
                table = scanner.nextLine();
                carDAO.create(connection, table);
                connection.close();
                return true;
            case TWO:
                System.out.print(READ_MESSAGE_CHOICE);
                console = Integer.parseInt(scanner.nextLine());

                if (console == ONE) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    listOfID = carDAO.readAllID(connection, table);

                    if (listOfID.size() == 0) {
                        System.out.println(EMPTY_TABLE);
                        connection.close();
                        return true;
                    }

                    System.out.print(REQUEST_ID);
                    id = Integer.parseInt(scanner.nextLine());

                    if (!carDAO.isIdPresent(listOfID, id)) {
                        connection.close();
                        return true;
                    }

                    dataTransferObject = carDAO.read(connection, table, id);

                    printer.print(dataTransferObject.getId(), dataTransferObject.getBrand(),
                            dataTransferObject.getAgeOfProduce());

                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    listOfID = carDAO.readAllID(connection, table);

                    if (listOfID.size() == 0) {
                        System.out.println(EMPTY_TABLE);
                        connection.close();
                        return true;
                    }

                    dataTransferObject = carDAO.readAll(connection, listOfID, table);

                    printer.printAll(dataTransferObject.getListOfID(), dataTransferObject.getBrandList(),
                            dataTransferObject.getAgeOfProduceList());

                } else {
                    System.out.println(INCORRECT_REQUEST);
                }
                connection.close();
                return true;
            case THREE:
                System.out.print(UPDATE_MESSAGE_CHOICE);
                console = Integer.parseInt(scanner.nextLine());
                if (console == ONE) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    System.out.print(REQUEST_BRAND);
                    final String brand = scanner.nextLine();

                    System.out.print(REQUEST_AGE_OF_PRODUCE);
                    final int ageOfProduce = Integer.parseInt(scanner.nextLine());

                    carDAO.update(connection, table, brand, ageOfProduce);
                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    listOfID = carDAO.readAllID(connection, table);

                    if (listOfID.size() == 0) {
                        System.out.println(EMPTY_TABLE);
                        connection.close();
                        return true;
                    }

                    System.out.print(REQUEST_ID);
                    id = Integer.parseInt(scanner.nextLine());

                    if (!carDAO.isIdPresent(listOfID, id)) {
                        connection.close();
                        return true;
                    }

                    System.out.print(REQUEST_COLUMN_NAME);
                    final String columnName = scanner.nextLine();

                    System.out.print(REQUEST_NEW_VALUE);
                    final String newValue = scanner.nextLine();

                    carDAO.update(connection, table, id, columnName, newValue);
                } else {
                    System.out.println(INCORRECT_REQUEST);
                }
                connection.close();
                return true;
            case FOUR:
                System.out.print(DELETE_CHOICE_MESSAGE);
                console = Integer.parseInt(scanner.nextLine());
                if (console == ONE) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();
                    carDAO.delete(connection, table);
                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    listOfID = carDAO.readAllID(connection, table);

                    if (listOfID.size() == 0) {
                        System.out.println(EMPTY_TABLE);
                        connection.close();
                        return true;
                    }

                    System.out.print(REQUEST_ID);
                    id = Integer.parseInt(scanner.nextLine());

                    if (!carDAO.isIdPresent(listOfID, id)) {
                        connection.close();
                        return true;
                    }

                    carDAO.delete(connection, table, id);
                } else {
                    System.out.println(INCORRECT_REQUEST);
                }
                connection.close();
                return true;
            case FIVE:
                connection.close();
                return false;
            default:
                System.out.println(INCORRECT_REQUEST);
                return true;
        }
    }

    public void getMessage() {
        System.out.print(MESSAGE);
    }
}
