import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private final static String MESSAGE = "1 для добавления таблицы\n" +
                                          "2 для просмотра информации в строке\n" +
                                          "3 для изменения или добавления строки\n" +
                                          "4 для удаления строки или таблицы\n" +
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
    private final static String DELETE_CHOICE_MESSAGE = "Введите 1 чтобы удалить таблицу или 2, чтобы Удалить строку: ";
    private final static String REQUEST_OLD_VALUE = "Введите старое значение: ";
    private final static String REQUEST_NEW_VALUE = "Введите новое значение: ";
    private final static String INCORRECT_REQUEST = "Неверный запрос, введите корректный запрос!";

    private final static String url = "jdbc:postgresql://localhost:5432/car";
    private final static String user = "postgres";
    private final static String password = "12345";

    public static void main(String[] args) throws SQLException {
        final Scanner scanner = new Scanner(System.in);
        final CarDAO carDAO = new CarDAO();
        int id;
        int console;
        String table;

        System.out.print(MESSAGE);

        while (true) {
            final Connection connection = DriverManager.getConnection(url, user, password);
            switch (Integer.parseInt(scanner.nextLine())) {
                case ONE:
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();
                    carDAO.create(connection, table);
                    connection.close();
                    break;
                case TWO:
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    System.out.print(REQUEST_ID);
                    id = Integer.parseInt(scanner.nextLine());

                    carDAO.read(connection, table, id);
                    connection.close();
                    break;
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

                        System.out.print(REQUEST_COLUMN_NAME);
                        final String columnName = scanner.nextLine();

                        System.out.print(REQUEST_OLD_VALUE);
                        final String oldValue = scanner.nextLine();

                        System.out.print(REQUEST_NEW_VALUE);
                        final String newValue = scanner.nextLine();

                        carDAO.update(connection, table, columnName, oldValue, newValue);
                    }
                    connection.close();
                    break;
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

                        System.out.print(REQUEST_ID);
                        id = Integer.parseInt(scanner.nextLine());

                        carDAO.delete(connection, table, id);
                    }
                    connection.close();
                    break;
                case FIVE:
                    connection.close();
                    return;
                default:
                    System.out.println(INCORRECT_REQUEST);
            }

            System.out.print(MESSAGE);
        }
    }
}
