import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DAO {
    private final String PATH_TO_FILE = "src/main/resources/init.sql";
    private final String FILE_NOT_FOUND_MESSAGE = "Файл не найден!";
    private final StatementFactory statementFactory = new StatementFactory();
    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    {
        final File file = new File(PATH_TO_FILE);
        final StringBuilder builder = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append("\n");
            }
        } catch (IOException e) {
            System.out.println(FILE_NOT_FOUND_MESSAGE);
        }
        final Connection connection = connectionFactory.createConnection();
        final PreparedStatement statement = statementFactory.prepareStatement(connection, builder.toString());

        statementFactory.executeStatement(statement);

        connectionFactory.closeConnection(connection);
        statementFactory.closeStatement(statement);
    }
}
