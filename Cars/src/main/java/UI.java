import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class UI {
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;
    private final static int FOUR = 4;
    private final static int FIVE = 5;
    private final static String URL = "url";
    private final static String USER = "user";
    private final static String PASSWORD = "password";
    private final static String BRAND = "brand";
    private final static String AGE_OF_PRODUCE = "age_of_produce";
    private final static String REQUEST_TABLE_NAME = "Введите имя таблицы: ";
    private final static String REQUEST_COLUMN_NAME = "Введите 1, чтобы изменить брэнд или 2, " +
                                                      "чтобы изменить год производства: ";
    private final static String REQUEST_ID = "Введите id: ";
    private final static String REQUEST_BRAND = "Введите брэнд: ";
    private final static String REQUEST_AGE_OF_PRODUCE = "Введите год изготовления: ";
    private final static String UPDATE_MESSAGE_CHOICE = "Введите 1 чтобы добавить строку или 2, чтобы изменить строку: ";
    private final static String DELETE_CHOICE_MESSAGE = "Введите 1 чтобы удалить таблицу или 2, чтобы удалить строку: ";
    private final static String REQUEST_NEW_VALUE = "Введите новое значение: ";
    private final static String READ_MESSAGE_CHOICE = "Введите 1 чтобы прочитать одну строку или 2, " +
                                                      "чтобы прочитать всю таблицу: ";
    private final static String INCORRECT_REQUEST = "Неверный запрос, введите корректный запрос!";
    private final static String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private final static String EXCEPTION_MESSAGE = "По данному пути файл не найдет!";

    private final static Scanner scanner = new Scanner(System.in);
    private final CarDAO carDAO = new CarDAO();

    public Connection createConnection() throws SQLException {
        final Properties properties = new Properties();

        try (final FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println(EXCEPTION_MESSAGE);
        }

        final String url = properties.getProperty(URL);
        final String user = properties.getProperty(USER);
        final String password = properties.getProperty(PASSWORD);

        return DriverManager.getConnection(url, user, password);
    }

    public boolean userInterface() throws SQLException {
        final Connection connection = createConnection();
        final Printer printer = new Printer();
        int id;
        int console;
        String table;
        final DataTransfer dataTransferObject;
        final int request;

        printer.printMessage();

        try {
            request = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            connection.close();
            return true;
        }

        switch (request) {
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

                    if (carDAO.isTableEmpty(connection, table)) {
                        connection.close();
                        return true;
                    }

                    carDAO.choiceOfId(connection, table);

                    System.out.print(REQUEST_ID);
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        connection.close();
                        return true;
                    }

                    if (!carDAO.isIdPresent(connection, table, id)) {
                        connection.close();
                        return true;
                    }

                    dataTransferObject = carDAO.read(connection, table, id);

                    printer.print(dataTransferObject.getListOfID(), dataTransferObject.getBrandList(),
                            dataTransferObject.getAgeOfProduceList());

                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableEmpty(connection, table)) {
                        connection.close();
                        return true;
                    }

                    dataTransferObject = carDAO.readAll(connection, table);

                    printer.print(dataTransferObject.getListOfID(), dataTransferObject.getBrandList(),
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
                    final int ageOfProduce;
                    try {
                        ageOfProduce = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        connection.close();
                        return true;
                    }

                    carDAO.update(connection, table, brand, ageOfProduce);
                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableEmpty(connection, table)) {
                        connection.close();
                        return true;
                    }

                    carDAO.choiceOfId(connection, table);

                    System.out.print(REQUEST_ID);
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        connection.close();
                        return true;
                    }

                    if (!carDAO.isIdPresent(connection, table, id)) {
                        connection.close();
                        return true;
                    }

                    System.out.print(REQUEST_COLUMN_NAME);
                    final int columnNumber;
                    try {
                        columnNumber = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        connection.close();
                        return true;
                    }
                    final String columnName;

                    if (columnNumber == ONE) {
                        columnName = BRAND;
                    } else if (columnNumber == TWO) {
                        columnName = AGE_OF_PRODUCE;
                    } else {
                        System.out.println(INCORRECT_REQUEST);
                        connection.close();
                        return true;
                    }
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

                    if (carDAO.isTableEmpty(connection, table)) {
                        connection.close();
                        return true;
                    }

                    carDAO.choiceOfId(connection, table);

                    System.out.print(REQUEST_ID);
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        connection.close();
                        return true;
                    }

                    if (!carDAO.isIdPresent(connection, table, id)) {
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
}
