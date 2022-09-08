import java.sql.SQLException;
import java.util.Scanner;

public class UI {
    private final static int ONE = 1;
    private final static int TWO = 2;
    private final static int THREE = 3;
    private final static int FOUR = 4;
    private final static int FIVE = 5;
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

    private final static Scanner scanner = new Scanner(System.in);
    private final CarDAO carDAO = new CarDAO();

    public boolean userInterface() throws SQLException {
        final Printer printer = new Printer();
        int id;
        int console;
        String table;
        final Car carObject;
        final int request;

        printer.printMessage();

        try {
            request = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return true;
        }

        switch (request) {
            case ONE:
                System.out.print(REQUEST_TABLE_NAME);
                table = scanner.nextLine();

                if (carDAO.isTableNameEmpty(table)) {
                    return true;
                }

                carDAO.create(table);
                return true;

            case TWO:
                System.out.print(READ_MESSAGE_CHOICE);
                try {
                    console = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return true;
                }

                if (console == ONE) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableNameEmpty(table)) {
                        return true;
                    }

                    if (carDAO.isTableEmpty(table)) {
                        return true;
                    }

                    carDAO.choiceOfId(table);

                    System.out.print(REQUEST_ID);
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                    }

                    if (!carDAO.isIdPresent(table, id)) {
                        return true;
                    }

                    carObject = carDAO.read(table, id);

                    printer.print(carObject.getListOfID(), carObject.getBrandList(),
                            carObject.getAgeOfProduceList());

                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableNameEmpty(table)) {
                        return true;
                    }

                    if (carDAO.isTableEmpty(table)) {
                        return true;
                    }

                    carObject = carDAO.readAll(table);

                    printer.print(carObject.getListOfID(), carObject.getBrandList(),
                            carObject.getAgeOfProduceList());

                } else {
                    System.out.println(INCORRECT_REQUEST);
                }
                return true;

            case THREE:
                System.out.print(UPDATE_MESSAGE_CHOICE);
                try {
                    console = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return true;
                }

                if (console == ONE) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableNameEmpty(table)) {
                        return true;
                    }

                    System.out.print(REQUEST_BRAND);
                    final String brand = scanner.nextLine();

                    System.out.print(REQUEST_AGE_OF_PRODUCE);
                    final int ageOfProduce;
                    try {
                        ageOfProduce = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                    }

                    carDAO.update(table, brand, ageOfProduce);
                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableNameEmpty(table)) {
                        return true;
                    }

                    if (carDAO.isTableEmpty(table)) {
                        return true;
                    }

                    carDAO.choiceOfId(table);

                    System.out.print(REQUEST_ID);
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                    }

                    if (!carDAO.isIdPresent(table, id)) {
                        return true;
                    }

                    System.out.print(REQUEST_COLUMN_NAME);
                    final int columnNumber;
                    try {
                        columnNumber = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                    }
                    final String columnName;

                    if (columnNumber == ONE) {
                        columnName = BRAND;
                    } else if (columnNumber == TWO) {
                        columnName = AGE_OF_PRODUCE;
                    } else {
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                    }
                    System.out.print(REQUEST_NEW_VALUE);
                    final String newValue = scanner.nextLine();

                    carDAO.update(table, id, columnName, newValue);
                } else {
                    System.out.println(INCORRECT_REQUEST);
                }
                return true;

            case FOUR:
                System.out.print(DELETE_CHOICE_MESSAGE);
                try {
                    console = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return true;
                }

                if (console == ONE) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableNameEmpty(table)) {
                        return true;
                    }

                    carDAO.deleteTable(table);
                } else if (console == TWO) {
                    System.out.print(REQUEST_TABLE_NAME);
                    table = scanner.nextLine();

                    if (carDAO.isTableNameEmpty(table)) {
                        return true;
                    }

                    if (carDAO.isTableEmpty(table)) {
                        return true;
                    }

                    carDAO.choiceOfId(table);

                    System.out.print(REQUEST_ID);
                    try {
                        id = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                    }

                    if (!carDAO.isIdPresent(table, id)) {
                        return true;
                    }

                    carDAO.delete(table, id);
                } else {
                    System.out.println(INCORRECT_REQUEST);
                }
                return true;
            case FIVE:
                return false;
            default:
                System.out.println(INCORRECT_REQUEST);
                return true;
        }
    }
}
