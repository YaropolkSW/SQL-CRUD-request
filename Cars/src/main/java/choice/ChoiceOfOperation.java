package choice;

public enum ChoiceOfOperation {

    CREATE,
    READ,
    UPDATE,
    DELETE,
    EXIT;

    public static ChoiceOfOperation getOperation(final int operation) {
        switch (operation) {
            case 1:
                return ChoiceOfOperation.CREATE;
            case 2:
                return ChoiceOfOperation.READ;
            case 3:
                return ChoiceOfOperation.UPDATE;
            case 4:
                return ChoiceOfOperation.DELETE;
            case 5:
                return ChoiceOfOperation.EXIT;
            default:
                throw new NumberFormatException();
        }
    }
}
