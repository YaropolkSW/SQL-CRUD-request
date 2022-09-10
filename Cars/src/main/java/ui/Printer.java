package ui;

import dao.Car;

public class Printer {
    private final static String MESSAGE = "1 для добавления таблицы\n" +
                                          "2 для просмотра таблицы\n" +
                                          "3 для добавления или изменения строки\n" +
                                          "4 для удаления таблицы или строки\n" +
                                          "5 для выхода\n" +
                                          "Введите запрос: ";
    private final static String STRING_SEPARATOR = "------------------------------";

    public void print(final Car car) {
        System.out.print(car);
    }

    public void printSeparator() {
        System.out.println(STRING_SEPARATOR);
    }

    public void printMessage() {
        System.out.print(MESSAGE);
    }
}
