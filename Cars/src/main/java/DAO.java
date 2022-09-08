import java.sql.Connection;
import java.sql.SQLException;

public interface DAO {
    void create(final String table) throws SQLException;

    Car read(final String table, final int id) throws SQLException;

    Car readAll(final String table) throws SQLException;

    void update(final String table, final String brand,
                final int ageOfProduce) throws SQLException;

    void update(final String table, final int id, final String column,
                final String newValue) throws SQLException;

    void deleteTable(final String table) throws SQLException;

    void delete(final String table, final int id) throws SQLException;

    boolean isIdPresent(final String table, final int id) throws SQLException;

    boolean isTableEmpty(final String table) throws SQLException;

    void choiceOfId(final String table) throws SQLException;

    boolean isTableNameEmpty(final String table);
}
