import java.util.List;

public interface CarDAO {
    void create(final String table);

    Car read(final String table, final int id);

    List<Car> readAll(final String table);

    void update(final String table, final String brand, final String model, final int ageOfProduce);

    void update(final String table, final int id, final String column, final String newValue);

    void deleteTable(final String table);

    void delete(final String table, final int id);

    void choiceOfId(final String table);
}
