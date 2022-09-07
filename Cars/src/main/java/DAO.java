import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    void create(final Connection connection, final String table) throws SQLException;

    DataTransfer read(final Connection connection, final String table, final int id) throws SQLException;

    DataTransfer readAll(final Connection connection, final List<Integer> listOfID,
            final String table) throws SQLException;

    List<Integer> readAllID(final Connection connection, final String table) throws SQLException;

    void update(final Connection connection, final String table, final String brand,
                final int ageOfProduce) throws SQLException;

    void update(final Connection connection, final String table, final int id, final String column,
                final String newValue) throws SQLException;

    void delete(final Connection connection, final String table) throws SQLException;

    void delete(final Connection connection, final String table, final int id) throws SQLException;
}
