public enum ChoiceOfOperation {

    CREATE(1),
    READ(2),
    UPDATE(3),
    DELETE(4),
    EXIT(5);

    private final int operation;

    ChoiceOfOperation(final int operation) {
        this.operation = operation;
    }

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
                return null;
        }
    }
}
