import java.sql.Connection;
import java.sql.SQLException;

public interface DAO {
    void create(final Connection connection, final String table) throws SQLException;

    DataTransfer read(final Connection connection, final String table, final int id) throws SQLException;

    DataTransfer readAll(final Connection connection, final String table) throws SQLException;

    void update(final Connection connection, final String table, final String brand,
                final int ageOfProduce) throws SQLException;

    void update(final Connection connection, final String table, final int id, final String column,
                final String newValue) throws SQLException;

    void delete(final Connection connection, final String table) throws SQLException;

    void delete(final Connection connection, final String table, final int id) throws SQLException;

    boolean isIdPresent(final Connection connection, final String table, final int id) throws SQLException;

    boolean isTableEmpty(final Connection connection, final String table) throws SQLException;

    void choiceOfId(final Connection connection, final String table) throws SQLException;
}
