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
        int id;
        final ChoiceOfInteraction interaction;
        final Car carObject;
        final List<Car> cars;
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
                carDAOImpl.createTable();
                return true;

            case READ:
                System.out.print(READ_MESSAGE_CHOICE);
                try {
                    interaction = ChoiceOfInteraction.getInteraction(Integer.parseInt(scanner.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return true;
                }

                switch (interaction) {
                    case INTERACTION_ONE:
                        carDAOImpl.choiceOfId();

                        System.out.print(REQUEST_ID);
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println(INCORRECT_REQUEST);
                            return true;
                        }

                        carObject = carDAOImpl.read(id);

                        printer.printSeparator();
                        printer.print(carObject);
                        printer.printSeparator();
                        return true;
                    case INTERACTION_TWO:
                        cars = carDAOImpl.readAll();

                        printer.printSeparator();
                        for (Car car : cars) {
                            printer.print(car);
                            printer.printSeparator();
                        }
                        return true;
                    default:
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                }

            case UPDATE:
                System.out.print(UPDATE_MESSAGE_CHOICE);
                try {
                    interaction = ChoiceOfInteraction.getInteraction(Integer.parseInt(scanner.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return true;
                }

                switch (interaction) {
                    case INTERACTION_ONE:
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
                            return true;
                        }

                        System.out.print(REQUEST_PRICE);
                        final int price;
                        try {
                            price = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println(INCORRECT_REQUEST);
                            return true;
                        }

                        carDAOImpl.save(brand, model, ageOfProduce, price);
                        return true;
                    case INTERACTION_TWO:
                        carDAOImpl.choiceOfId();

                        System.out.print(REQUEST_ID);
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println(INCORRECT_REQUEST);
                            return true;
                        }

                        System.out.print(REQUEST_NEW_PRICE);
                        final int newPrice;
                        try {
                            newPrice = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println(INCORRECT_REQUEST);
                            return true;
                        }

                        carDAOImpl.update(id, newPrice);
                            return true;
                    default:
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                }

            case DELETE:
                System.out.print(DELETE_CHOICE_MESSAGE);
                try {
                    interaction = ChoiceOfInteraction.getInteraction(Integer.parseInt(scanner.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println(INCORRECT_REQUEST);
                    return true;
                }

                switch (interaction) {
                    case INTERACTION_ONE:
                        carDAOImpl.deleteTable();
                        return true;
                    case INTERACTION_TWO:
                        carDAOImpl.choiceOfId();

                        System.out.print(REQUEST_ID);
                        try {
                            id = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println(INCORRECT_REQUEST);
                            return true;
                        }

                        carDAOImpl.delete(id);
                        return true;
                    default:
                        System.out.println(INCORRECT_REQUEST);
                        return true;
                }
            case EXIT:
                return false;
            default:
                System.out.println(INCORRECT_REQUEST);
                return true;
        }
    }
}
