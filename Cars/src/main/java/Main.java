import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private final static String CREATE = "-c";
    private final static String READ = "-r";
    private final static String UPDATE = "-u";
    private final static String DELETE = "-d";
    private final static String SPACE = " ";
    private final static String REQUEST_TABLE_NAME = "Введите имя таблицы: ";
    private final static String UPDATE_MESSAGE_CHOICE = "Вы хотите добавить строку или изменить строку?\n" +
            "Введите \"Добавить\" или \"Изменить\": ";
    private final static String ADD = "Добавить";
    private final static String CHANGE = "Изменить";
    private final static String NEW_LINE_MESSAGE = "Введите имя таблицы, brand, age_of_creation через пробел: ";
    private final static String CHANGE_LINE_MESSAGE = "Введите имя таблицы, название столбца, который хотите " +
            "изменить,\nа так же старое и новое значение через пробел: ";
    private final static String DELETE_CHOICE_MESSAGE = "Вы хотите удалить таблицу или строку?\n" +
            "Введите \"Удалить таблицу\" или \"Удалить строку\": ";
    private final static String DELETE_LINE_MESSAGE = "Удалить строку";
    private final static String DELETE_TABLE_MESSAGE = "Удалить таблицу";
    private final static String REQUEST_OF_DATA = "Введите имя таблицы и id через пробел: ";

    private final static String url = "jdbc:postgresql://localhost:5432/car";
    private final static String user = "postgres";
    private final static String password = "12345";

    public static void main(String[] args) throws SQLException {
        final Connection connection = DriverManager.getConnection(url, user, password);
        final Scanner scanner = new Scanner(System.in);
        final CRUD crud = new CRUD();
        final String table;
        final String console;

        switch (scanner.nextLine()) {
            case CREATE:
                System.out.print(REQUEST_TABLE_NAME);
                table = scanner.nextLine();
                crud.create(connection, table);
                connection.close();
                break;
            case READ:
                System.out.print(REQUEST_TABLE_NAME);
                table = scanner.nextLine();
                crud.read(connection, table);
                connection.close();
                break;
            case UPDATE:
                System.out.print(UPDATE_MESSAGE_CHOICE);
                console = scanner.nextLine();
                if (console.equalsIgnoreCase(ADD)) {
                    System.out.print(NEW_LINE_MESSAGE);
                    table = scanner.nextLine();
                    final String[] addInfo = table.split(SPACE);
                    crud.update(connection, addInfo[0], addInfo[1], Integer.parseInt(addInfo[2]));
                } else if (console.equalsIgnoreCase(CHANGE)) {
                    System.out.print(CHANGE_LINE_MESSAGE);
                    table = scanner.nextLine();
                    final String[] changeInfo = table.split(SPACE);
                    crud.update(connection, changeInfo[0], changeInfo[1], changeInfo[2], changeInfo[3]);
                }
                connection.close();
                break;
            case DELETE:
                System.out.print(DELETE_CHOICE_MESSAGE);
                console = scanner.nextLine();
                if (console.equalsIgnoreCase(DELETE_TABLE_MESSAGE)) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();
                    crud.delete(connection, table);
                } else if (console.equalsIgnoreCase(DELETE_LINE_MESSAGE)) {
                    System.out.print(REQUEST_OF_DATA);
                    table = scanner.nextLine();
                    final String[] deleteInfo = table.split(SPACE);
                    crud.delete(connection, deleteInfo[0], Integer.parseInt(deleteInfo[1]));
                }
                connection.close();
                break;
        }
    }
}
