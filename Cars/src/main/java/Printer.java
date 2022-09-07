import java.util.List;

public class Printer {
    private final static String MESSAGE = "1 для добавления таблицы\n" +
                                          "2 для просмотра таблицы\n" +
                                          "3 для добавления или изменения строки\n" +
                                          "4 для удаления таблицы или строки\n" +
                                          "5 для выхода\n" +
                                          "Введите запрос: ";
    private final static String STRING_SEPARATOR = "------------------------------";
    private final static String OUTPUT_PATTERN = "ID - %d\nBrand - %s\nAge of produce - %d\n";
    private int index = 0;

    public void print(final List<Integer> idList, final List<String> brandList, final List<Integer> ageOfProduceList) {
        System.out.println(STRING_SEPARATOR);
        for (Integer element : idList) {
            System.out.print(String.format(OUTPUT_PATTERN, element, brandList.get(index), ageOfProduceList.get(index)));
            System.out.println(STRING_SEPARATOR);
            index++;
        }
    }

    public void printMessage() {
        System.out.print(MESSAGE);
    }
}
