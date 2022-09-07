import java.util.List;

public class Printer {
    private final static String STRING_SEPARATOR = "------------------------------";
    private final static String OUTPUT_PATTERN = "ID - %d\nBrand - %s\nAge of produce - %d\n";
    private int index = 0;

    public void print(final int id, final String brand, final int age) {
        System.out.println(STRING_SEPARATOR);
        System.out.print(String.format(OUTPUT_PATTERN, id, brand, age));
        System.out.println(STRING_SEPARATOR);
    }

    public void printAll(final List<Integer> idList, final List<String> brandList, final List<Integer> ageOfProduceList) {
        System.out.println(STRING_SEPARATOR);
        for (Integer element : idList) {
            System.out.print(String.format(OUTPUT_PATTERN, element, brandList.get(index), ageOfProduceList.get(index)));
            System.out.println(STRING_SEPARATOR);
            index++;
        }
    }
}
