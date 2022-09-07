import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        final UI ui = new UI();
        boolean isContinue = true;

        while (isContinue) {
            isContinue = ui.userInterface();
        }
    }
}
