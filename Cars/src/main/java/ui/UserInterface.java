package ui;

import choice.ChoiceOfDelete;
import choice.ChoiceOfOperation;
import choice.ChoiceOfRead;
import choice.ChoiceOfUpdate;
import dao.Car;
import dao.CarDAOImpl;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final static String REQUEST_ID = "Введите id: ";
    private final static String REQUEST_BRAND = "Введите брэнд: ";
    private final static String REQUEST_MODEL = "Введите модель: ";
    private final static String REQUEST_AGE_OF_PRODUCE = "Введите год изготовления: ";
    private final static String REQUEST_PRICE = "Введите цену: ";
    private final static String UPDATE_MESSAGE_CHOICE = "Введите 1 чтобы добавить строку или 2, чтобы изменить строку: ";
    private final static String DELETE_CHOICE_MESSAGE = "Введите 1 чтобы удалить таблицу или 2, чтобы удалить строку: ";
    private final static String REQUEST_NEW_PRICE = "Введите новую цену: ";
    private final static String READ_MESSAGE_CHOICE = "Введите 1 чтобы прочитать одну строку или 2, " +
            "чтобы прочитать всю таблицу: ";
    private final static String INCORRECT_REQUEST = "Некорректный параметр!";

    private final static Scanner scanner = new Scanner(System.in);
    private final CarDAOImpl carDAOImpl = new CarDAOImpl();

    public boolean showUserInterface() {
        final Printer printer = new Printer();
        final ChoiceOfOperation operation;

        printer.printMessage();

        try {
            operation = ChoiceOfOperation.getOperation(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return true;
        }

        switch (operation) {
            case CREATE:
                create();
                return true;

            case READ:
                read();
                return true;

            case UPDATE:
                update();
                return true;

            case DELETE:
                delete();
                return true;

            case EXIT:
                return false;

            default:
                System.out.println(INCORRECT_REQUEST);
                return true;
        }
    }

    private void create() {
        carDAOImpl.createTable();
    }

    private void read() {
        final Printer printer = new Printer();
        final int id;
        final Car carObject;
        final List<Car> cars;
        final ChoiceOfRead interaction;

        System.out.print(READ_MESSAGE_CHOICE);
        try {
            interaction = ChoiceOfRead.getInteraction(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        switch (interaction) {
            case READ_ONE_LINE:
                carDAOImpl.choiceOfId();

                System.out.print(REQUEST_ID);
                try {
                    id = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return;
                }

                carObject = carDAOImpl.read(id);

                printer.printSeparator();
                printer.print(carObject);
                printer.printSeparator();

                break;

            case READ_ALL_LINES:
                cars = carDAOImpl.readAll();

                printer.printSeparator();
                for (Car car : cars) {
                    printer.print(car);
                    printer.printSeparator();
                }

                break;

            default:
                System.out.println(INCORRECT_REQUEST);
        }
    }

    private void update() {
        final int id;
        final ChoiceOfUpdate interaction;

        System.out.print(UPDATE_MESSAGE_CHOICE);
        try {
            interaction = ChoiceOfUpdate.getInteraction(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        switch (interaction) {
            case ADD_LINE:
                System.out.print(REQUEST_BRAND);
                final String brand = scanner.nextLine();

                System.out.print(REQUEST_MODEL);
                final String model = scanner.nextLine();

                System.out.print(REQUEST_AGE_OF_PRODUCE);
                final int ageOfProduce;
                try {
                    ageOfProduce = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return;
                }

                System.out.print(REQUEST_PRICE);
                final int price;
                try {
                    price = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return;
                }

                carDAOImpl.save(brand, model, ageOfProduce, price);
                break;
            case CHANGE_LINE:
                carDAOImpl.choiceOfId();

                System.out.print(REQUEST_ID);
                try {
                    id = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return;
                }

                System.out.print(REQUEST_NEW_PRICE);
                final int newPrice;
                try {
                    newPrice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return;
                }

                carDAOImpl.update(id, newPrice);
                break;
            default:
                System.out.println(INCORRECT_REQUEST);
        }
    }

    private void delete() {
        final int id;
        final ChoiceOfDelete interaction;

        System.out.print(DELETE_CHOICE_MESSAGE);
        try {
            interaction = ChoiceOfDelete.getInteraction(Integer.parseInt(scanner.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println(INCORRECT_REQUEST);
            return;
        }

        switch (interaction) {
            case DELETE_TABLE:
                carDAOImpl.deleteTable();
                break;
            case DELETE_LINE:
                carDAOImpl.choiceOfId();

                System.out.print(REQUEST_ID);
                try {
                    id = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return;
                }

                carDAOImpl.delete(id);
                break;
            default:
                System.out.println(INCORRECT_REQUEST);
        }
    }
}
